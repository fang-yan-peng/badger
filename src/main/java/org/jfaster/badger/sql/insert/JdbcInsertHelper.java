package org.jfaster.badger.sql.insert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import javax.sql.DataSource;

import org.jfaster.badger.Badger;
import org.jfaster.badger.dialect.Dialect;
import org.jfaster.badger.dialect.InsertResult;
import org.jfaster.badger.exception.BadgerException;
import org.jfaster.badger.jdbc.type.TypeHandler;
import org.jfaster.badger.jdbc.type.TypeHandlerRegistry;
import org.jfaster.badger.query.bean.invoker.SetterInvoker;
import org.jfaster.badger.query.shard.ShardResult;
import org.jfaster.badger.spi.ExtensionLoader;
import org.jfaster.badger.sql.UpdateOpTransfer;
import org.jfaster.badger.sql.interceptor.SqlInterceptor;
import org.jfaster.badger.transaction.ConnectionManager;
import org.jfaster.badger.util.ExceptionUtils;
import org.jfaster.badger.util.JdbcUtils;
import org.jfaster.badger.util.Joiner;
import org.jfaster.badger.util.ManualShardUtils;
import org.jfaster.badger.util.ShardUtils;
import org.jfaster.badger.util.SqlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 插入辅助类
 * @author yanpengfang
 * create 2019-01-11 6:40 PM
 */
public class JdbcInsertHelper {

    private static final Logger logger = LoggerFactory.getLogger(JdbcInsertHelper.class);

    /**
     * 插入记录，去除非空字段
     * @param t
     * @param ignore
     * @param badger
     * @return
     */
    public static int insertNotNull(Object t, boolean ignore, Badger badger) {
        return execute(t, true, ShardUtils.shard(t, false), ignore, badger);
    }

    /**
     * 插入所有记录
     * @param t
     * @param ignore
     * @param badger
     * @return
     */
    public static int insert(Object t, boolean ignore, Badger badger) {
        return execute(t, false, ShardUtils.shard(t, false), ignore, badger);
    }

    /**
     * 插入记录，去除非空字段
     * @param t
     * @param ignore
     * @param badger
     * @return
             */
    public static int insertNotNull(Object t, boolean ignore, Object shardValue ,Badger badger) {
        return execute(t, true, ManualShardUtils.shard(t, shardValue), ignore, badger);
    }

    /**
     * 插入所有记录
     * @param t
     * @param ignore
     * @param badger
     * @return
     */
    public static int insert(Object t, boolean ignore, Object shardValue ,Badger badger) {
        return execute(t, false, ManualShardUtils.shard(t, shardValue), ignore, badger);
    }

    private static int execute(Object t, boolean notNull, ShardResult shardRes,
            boolean ignore, Badger badger) {
        String table = shardRes.getTableName();
        String dbName = shardRes.getDataSourceName();
        Class<?> clazz = t.getClass();
        SqlInterceptor interceptor = badger.getInterceptor();
        boolean hasInterceptor = interceptor != null;
        InsertResult sqlRs;
        Dialect dialect = ExtensionLoader.get(Dialect.class).getExtension(badger.getDialect());
        if (dialect == null) {
            throw new BadgerException("dialect %s is not found", badger.getDialect());
        }
        if (table == null) {
            sqlRs = notNull ? dialect.insertSqlNotNull(t, ignore)
                    : dialect.insertSql(t, ignore);
        } else {
            sqlRs = notNull ? dialect.insertSqlNotNull(t, table, ignore)
                    : dialect.insertSql(t, table, ignore);
        }
        String sql = sqlRs.getSql();
        boolean hasPk = sqlRs.isHasPk();
        List<Object> values = sqlRs.getValues();
        DataSource dataSource = badger.getMasterDataSource(dbName);
        ConnectionManager manager = ExtensionLoader.get(ConnectionManager.class).getExtension(badger.getTransactionManager());
        if (manager == null) {
            throw new BadgerException("transactionManager:%s not found");
        }
        Connection conn = manager.getConnection(dataSource);
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            if (hasPk) {
                ps = conn.prepareStatement(sql);
            } else {
                ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            }
            ps.setQueryTimeout(badger.getUpdateTimeout());
            if (values != null) {
                for (int i = 0; i < values.size(); i++) {
                    Object arg = values.get(i);
                    TypeHandler typeHandler = TypeHandlerRegistry.getTypeHandler(arg.getClass());
                    typeHandler.setParameter(ps, i + 1, arg);
                }
            } else {
                List<String> columns = hasPk ? SqlUtils.getAllColumns(clazz) : SqlUtils.getColumns(clazz);
                for (int i = 0; i < columns.size(); ++i) {
                    Object arg = SqlUtils.getValueByColumn(t, columns.get(i));
                    TypeHandler typeHandler = TypeHandlerRegistry.getTypeHandler(arg.getClass());
                    typeHandler.setParameter(ps, i + 1, arg);
                }
            }
            if (hasInterceptor) {
                ExceptionUtils.runIgnoreThrowable(() -> interceptor.before(sql));
            }
            int effectCount = ps.executeUpdate();
            if (hasInterceptor) {
                ExceptionUtils.runIgnoreThrowable(() -> interceptor.after(sql));
            }
            if (ignore && effectCount == 0) {
                return 0;
            }
            if (!hasPk) { // 生成自增key
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    SetterInvoker invoker = SqlUtils.getIdSetterInvoker(clazz);
                    if (invoker != null) {
                        UpdateOpTransfer.GeneratedTransformer gt = UpdateOpTransfer.GENERATED_TRANSFORMERS.get(invoker.getRawType());
                        if (gt == null) {
                            String expected = Joiner.on(",").join(UpdateOpTransfer.GENERATED_TRANSFORMERS.keySet());
                            throw new BadgerException("the return type of update(returnGeneratedId) " +
                                    "expected one of " + expected + " but " + invoker.getRawType());
                        }
                        TypeHandler<? extends Number> generatedKeyTypeHandler = TypeHandlerRegistry.getTypeHandler(gt.getRawType());
                        Number key = generatedKeyTypeHandler.getResult(rs, 1);
                        invoker.invoke(t, gt.transform(key));
                    }
                }
            }
            if (logger.isDebugEnabled()) {
                logger.debug(logString(t, sql, clazz, null, true));
            }
            return effectCount;
        } catch (Throwable e) {
            String str = logString(t, sql, clazz, e, true);
            logger.error(str, e);
            if (hasInterceptor) {
                ExceptionUtils.runIgnoreThrowable(() -> interceptor.error(sql, e));
            }
            throw new BadgerException(str, e);
        } finally {
            JdbcUtils.closeResultSet(rs);
            JdbcUtils.closeStatement(ps);
            manager.releaseConnection(conn, dataSource);
            if (hasInterceptor) {
                SqlInterceptor.clear();
            }
        }
    }

    private static String logString(Object t, String sql, Class<?> clazz, Throwable e, boolean notNull) {
        StringBuilder sb = new StringBuilder();
        sb.append("执行sql='").append(sql);
        if (e != null) {
            sb.append("'失败，错误原因：").append(e.getMessage());
        }
        sb.append("\'\n");
        sb.append("参数：");
        List<String> columns = SqlUtils.hasPKValue(t) ? (SqlUtils.getAllColumns(clazz)) : (SqlUtils.getColumns(clazz));
        for (String column : columns) {
            Object value = SqlUtils.getValueByColumn(t, column);
            if (notNull) {
                if (value != null) {
                    sb.append(column).append("=").append(value).append(";");
                }
            } else {
                sb.append(column).append("=").append(value).append(";");
            }
        }
        sb.append('\n');
        return sb.toString();
    }

}
