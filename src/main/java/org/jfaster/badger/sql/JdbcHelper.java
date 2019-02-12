package org.jfaster.badger.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.jfaster.badger.Badger;
import org.jfaster.badger.exception.BadgerException;
import org.jfaster.badger.exception.MappingException;
import org.jfaster.badger.jdbc.extractor.ResultSetExtractor;
import org.jfaster.badger.jdbc.type.TypeHandler;
import org.jfaster.badger.jdbc.type.TypeHandlerRegistry;
import org.jfaster.badger.jdbc.type.convert.TypeConverter;
import org.jfaster.badger.query.bean.invoker.GetterInvoker;
import org.jfaster.badger.sql.interceptor.SqlInterceptor;
import org.jfaster.badger.sql.update.JdbcUpdateHelper;
import org.jfaster.badger.transaction.DataSourceUtils;
import org.jfaster.badger.util.ExceptionUtils;
import org.jfaster.badger.util.JdbcUtils;
import org.jfaster.badger.util.SqlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author yanpengfang
 * @create 2019-01-22 2:43 PM
 */
public class JdbcHelper {

    private final static Logger logger = LoggerFactory.getLogger(JdbcUpdateHelper.class);

    public static <T> int executeUpdate(Badger badger, Class<T> clazz,
            List<String> dynamicFields, String dbName, List<Object> parameters, String sql, boolean convert) {
        SqlInterceptor interceptor = badger.getInterceptor();
        boolean hasInterceptor = interceptor != null;
        int res;
        DataSource dataSource = badger.getMasterDataSource(dbName);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        PreparedStatement ps = null;
        Statement st = null;
        try {
            if (dynamicFields.isEmpty()) {
                st = conn.createStatement();
                res = executeUpdate(st, sql, badger, hasInterceptor, interceptor);
            } else {
                ps = conn.prepareStatement(sql);
                ps.setQueryTimeout(badger.getUpdateTimeout());
                setParameters(dynamicFields, clazz, parameters, ps, convert);
                if (hasInterceptor) {
                    ExceptionUtils.runIgnoreThrowable(() -> interceptor.before(sql));
                }
                res = ps.executeUpdate();
                if (hasInterceptor) {
                    ExceptionUtils.runIgnoreThrowable(() -> interceptor.after(sql));
                }
            }
            if (logger.isDebugEnabled()) {
                logger.debug(logString(sql, null, parameters));
            }
        } catch (Exception e) {
            String str = logString(sql, e, parameters);
            logger.error(str, e);
            if (hasInterceptor) {
                ExceptionUtils.runIgnoreThrowable(() -> interceptor.error(sql, e));
            }
            throw new BadgerException(str, e);
        } finally {
            closeResource(conn, dataSource, hasInterceptor, ps, st);
        }
        return res;
    }

    public static int executeUpdate(Badger badger, String sql, String dbName, List<Object> parameters) throws Exception {
        SqlInterceptor interceptor = badger.getInterceptor();
        boolean hasInterceptor = interceptor != null;
        int res;
        DataSource dataSource = badger.getMasterDataSource(dbName);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        PreparedStatement ps = null;
        Statement st = null;
        try {
            if (parameters == null || parameters.isEmpty()) {
                st = conn.createStatement();
                res = executeUpdate(st, sql, badger, hasInterceptor, interceptor);
            } else {
                ps = conn.prepareStatement(sql);
                ps.setQueryTimeout(badger.getUpdateTimeout());
                setParameters(parameters, ps);
                if (hasInterceptor) {
                    ExceptionUtils.runIgnoreThrowable(() -> interceptor.before(sql));
                }
                res = ps.executeUpdate();
                if (hasInterceptor) {
                    ExceptionUtils.runIgnoreThrowable(() -> interceptor.after(sql));
                }
            }
            if (logger.isDebugEnabled()) {
                logger.debug(logString(sql, null, parameters));
            }
        } catch (Exception e) {
            String str = logString(sql, e, parameters);
            logger.error(str, e);
            if (hasInterceptor) {
                ExceptionUtils.runIgnoreThrowable(() -> interceptor.error(sql, e));
            }
            throw new BadgerException(str, e);
        } finally {
            closeResource(conn, dataSource, hasInterceptor, ps, st);
        }
        return res;
    }

    public static <T, O> O executeQuery(Badger badger, Class<T> clazz, List<String> dynamicFields,
            String dbName, List<Object> parameters, String sql,
            ResultSetExtractor<O> extractor, boolean useMaster, boolean convert) throws Exception {
        DataSource dataSource = useMaster ? badger.getMasterDataSource(dbName) : badger.getSlaveDataSource(dbName);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        SqlInterceptor interceptor = badger.getInterceptor();
        boolean hasInterceptor = interceptor != null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        Statement st = null;
        O res;
        try {
            if (dynamicFields.isEmpty()) {
                st = conn.createStatement();
                rs = executeQuery(st, sql, badger, hasInterceptor, interceptor);
            } else {
                ps = conn.prepareStatement(sql);
                ps.setQueryTimeout(badger.getQueryTimeout());
                setParameters(dynamicFields, clazz, parameters, ps, convert);
                if (hasInterceptor) {
                    ExceptionUtils.runIgnoreThrowable(() -> interceptor.before(sql));
                }
                rs = ps.executeQuery();
                if (hasInterceptor) {
                    ExceptionUtils.runIgnoreThrowable(() -> interceptor.after(sql));
                }
            }
            res = extractor.extractData(rs);
            if (logger.isDebugEnabled()) {
                logger.debug(JdbcHelper.logString(sql, null, parameters));
            }
        } catch (Exception e) {
            String str = JdbcHelper.logString(sql, e, parameters);
            if (hasInterceptor) {
                ExceptionUtils.runIgnoreThrowable(() -> interceptor.error(sql, e));
            }
            logger.error(str, e);
            throw new BadgerException(str, e);
        } finally {
            closeResource(conn, dataSource, hasInterceptor, rs, ps, st);
        }
        return res;
    }

    public static <O> O executeQuery(Badger badger, String dbName, List<Object> parameters, String sql,
            ResultSetExtractor<O> extractor, boolean useMaster) throws Exception {
        DataSource dataSource = useMaster ? badger.getMasterDataSource(dbName) : badger.getSlaveDataSource(dbName);
        Connection conn = DataSourceUtils.getConnection(dataSource);
        SqlInterceptor interceptor = badger.getInterceptor();
        boolean hasInterceptor = interceptor != null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        Statement st = null;
        O res;
        try {
            if (parameters == null || parameters.isEmpty()) {
                st = conn.createStatement();
                rs = executeQuery(st, sql, badger, hasInterceptor, interceptor);
            } else {
                ps = conn.prepareStatement(sql);
                ps.setQueryTimeout(badger.getQueryTimeout());
                setParameters(parameters, ps);
                if (hasInterceptor) {
                    ExceptionUtils.runIgnoreThrowable(() -> interceptor.before(sql));
                }
                rs = ps.executeQuery();
                if (hasInterceptor) {
                    ExceptionUtils.runIgnoreThrowable(() -> interceptor.after(sql));
                }
            }
            res = extractor.extractData(rs);
            if (logger.isDebugEnabled()) {
                logger.debug(JdbcHelper.logString(sql, null, parameters));
            }
        } catch (Exception e) {
            String str = JdbcHelper.logString(sql, e, parameters);
            if (hasInterceptor) {
                ExceptionUtils.runIgnoreThrowable(() -> interceptor.error(sql, e));
            }
            logger.error(str, e);
            throw new BadgerException(str, e);
        } finally {
            closeResource(conn, dataSource, hasInterceptor, rs, ps, st);
        }
        return res;
    }

    private static void closeResource(Connection conn, DataSource dataSource,
            boolean hasInterceptor,
            ResultSet rs,
            PreparedStatement ps, Statement st) {
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(ps);
        JdbcUtils.closeStatement(st);
        DataSourceUtils.releaseConnection(conn, dataSource);
        if (hasInterceptor) {
            SqlInterceptor.clear();
        }
    }

    private static void closeResource(Connection conn, DataSource dataSource,
            boolean hasInterceptor,
            PreparedStatement ps,
            Statement st) {
        JdbcUtils.closeStatement(ps);
        JdbcUtils.closeStatement(st);
        DataSourceUtils.releaseConnection(conn, dataSource);
        if (hasInterceptor) {
            SqlInterceptor.clear();
        }
    }

    @SuppressWarnings("ALL")
    private static <T> void setParameters(List<String> dynamicFields, Class<T> clazz, List<Object>
            parameters, PreparedStatement ps, boolean convert) throws SQLException {
        int size = dynamicFields.size();
        for (int i = 0; i < size; ++i) {
            Object param = parameters.get(i);
            GetterInvoker invoker = SqlUtils.getGetterInvoker(clazz, dynamicFields.get(i));
            if (invoker == null) {
                throw new MappingException("%s 没有setter getter方法", dynamicFields.get(i));
            }
            if (convert) {
                TypeConverter converter = invoker.getConverter();
                if (converter != null) {
                    param = converter.convert(param);
                }
            }
            TypeHandler typeHandler = TypeHandlerRegistry.getTypeHandler(invoker.getJdbcType());
            typeHandler.setParameter(ps, i + 1, param);
        }
    }

    @SuppressWarnings("ALL")
    private static void setParameters(List<Object>
            parameters, PreparedStatement ps) throws SQLException {
        int size = parameters.size();
        for (int i = 0; i < size; ++i) {
            Object param = parameters.get(i);
            TypeHandler typeHandler = TypeHandlerRegistry.getTypeHandler(param.getClass());
            typeHandler.setParameter(ps, i + 1, param);
        }
    }

    private static int executeUpdate(Statement st,
            String sql, Badger badger, boolean hasInterceptor,
            SqlInterceptor interceptor) throws Exception {
        st.setQueryTimeout(badger.getUpdateTimeout());
        if (hasInterceptor) {
            ExceptionUtils.runIgnoreThrowable(() -> interceptor.before(sql));
        }
        int res = st.executeUpdate(sql);
        if (hasInterceptor) {
            ExceptionUtils.runIgnoreThrowable(() -> interceptor.after(sql));
        }
        return res;
    }

    private static ResultSet executeQuery(Statement st,
            String sql, Badger badger, boolean hashInterceptor,
            SqlInterceptor interceptor) throws Exception {
        st.setQueryTimeout(badger.getUpdateTimeout());
        if (hashInterceptor) {
            ExceptionUtils.runIgnoreThrowable(() -> interceptor.before(sql));
        }
        ResultSet res = st.executeQuery(sql);
        if (hashInterceptor) {
            ExceptionUtils.runIgnoreThrowable(() -> interceptor.after(sql));
        }
        return res;
    }

    private static String logString(String sql, Exception e, List<Object> paramList) {
        StringBuilder sb = new StringBuilder();
        sb.append("执行sql='").append(sql);
        if (e != null) {
            sb.append("'失败，错误原因：").append(e.getMessage());
        }
        sb.append("\'\n");
        sb.append("参数：");
        if (paramList != null) {
            int index = 1;
            for (Object obj : paramList) {
                sb.append("param").append(index).append('=').append(obj).append(";");
                index++;
            }
        }
        sb.append('\n');
        return sb.toString();
    }
}
