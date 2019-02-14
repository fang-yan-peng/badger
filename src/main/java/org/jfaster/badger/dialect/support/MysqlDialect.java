package org.jfaster.badger.dialect.support;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.jfaster.badger.dialect.Dialect;
import org.jfaster.badger.dialect.InsertResult;
import org.jfaster.badger.exception.BadgerException;
import org.jfaster.badger.exception.MappingException;
import org.jfaster.badger.spi.SpiMeta;
import org.jfaster.badger.util.SqlUtils;
import org.jfaster.badger.util.Strings;

/**
 * mysql数据库构造sql
 * @author yanpengfang
 * create 2019-01-03 9:45 PM
 */
@SuppressWarnings("ALL")
@SpiMeta(name = "mysql")
public class MysqlDialect implements Dialect {

    private Map<String, String> insertSqlMap = new ConcurrentHashMap<>();
    private Map<Class<?>, String> getSqlMap = new ConcurrentHashMap<>();
    private Map<Class<?>, String> deleteSqlMap = new ConcurrentHashMap<>();
    private Map<Class<?>, String> updateSqlMap = new ConcurrentHashMap<>();
    private Map<Class<?>, String> selectSqlMap = new ConcurrentHashMap<>();
    private Map<Class<?>, String> countSqlMap = new ConcurrentHashMap<>();

    /**
     * 由于要判断对象的属性是否为空，所以即使是同一个类sql也有可能不同，所以不缓存sql。
     * @param t
     * @param ignore
     * @return
     */
    @Override
    public InsertResult insertSqlNotNull(Object t, boolean ignore) {
        return this.insertSqlNotNull(t, SqlUtils.getTableInfo(t.getClass()).getTableName(), ignore);
    }

    /**
     * 由于要判断对象的属性是否为空，所以即使是同一个类sql也有可能不同，并且由于分表sql也不尽相同，所以不缓存sql。
     * @param t
     * @param tableName
     * @param ignore
     * @return
     */
    @Override
    public InsertResult insertSqlNotNull(Object t, String tableName, boolean ignore) {
        Class<?> clazz = t.getClass();
        InsertResult rs = new InsertResult();
        StringBuilder sql = new StringBuilder("INSERT");
        if (ignore) {
            sql.append(" IGNORE");
        }
        sql.append(" INTO `");
        sql.append(tableName);
        sql.append("` (");
        boolean hasPKValue = SqlUtils.hasPKValue(t);
        rs.setHasPk(hasPKValue);
        List<String> columns = hasPKValue ? SqlUtils.getAllColumns(clazz) : SqlUtils.getColumns(clazz);
        StringBuilder sbColumn = new StringBuilder();
        StringBuilder sbValue = new StringBuilder();
        boolean first = true;
        List<Object> values = new ArrayList<>();
        for (String column : columns) {
            Object val = SqlUtils.getValueByColumn(t, column);
            if (val != null) {
                values.add(val);
                if (!first) {
                    sbColumn.append(",");
                    sbValue.append(",");
                }
                sbColumn.append("`");
                sbColumn.append(column);
                sbColumn.append("`");
                sbValue.append("?");
                first = false;
            }
        }
        sql.append(sbColumn);
        sql.append(") VALUES (");
        sql.append(sbValue);
        sql.append(")");
        rs.setValues(values);
        rs.setSql(sql.toString());
        return rs;
    }

    /**
     * 直接插入所以字段，不根据对象判断所以可以缓存sql。
     * @param t
     * @param ignore
     * @return
     */
    @Override
    public InsertResult insertSql(Object t, boolean ignore) {
        Class<?> clazz = t.getClass();
        InsertResult rs = new InsertResult();
        boolean hasPKValue = SqlUtils.hasPKValue(t);
        rs.setHasPk(hasPKValue);
        String sql;
        String key = clazz.getName();
        if (hasPKValue) {
            key += "-has" + String.valueOf(ignore);
            sql = insertSqlMap.get(key);
        } else {
            key += "-no" + String.valueOf(ignore);
            sql = insertSqlMap.get(key);
        }
        if (sql != null) {
            rs.setSql(sql);
            return rs;
        }
        StringBuilder sbSql = new StringBuilder("INSERT");
        if (ignore) {
            sbSql.append(" IGNORE");
        }
        sbSql.append(" INTO `");
        sbSql.append(SqlUtils.getTableInfo(clazz).getTableName());
        sbSql.append("` (");
        List<String> columns = hasPKValue ? (SqlUtils.getAllColumns(clazz)) : (SqlUtils.getColumns(clazz));
        if (columns.isEmpty()) {
            throw new BadgerException(clazz.getName() + " not exist columns");
        }
        StringBuilder sbColumn = new StringBuilder();
        sbColumn.append("`").append(columns.get(0)).append("`");
        StringBuilder sbValue = new StringBuilder("?");
        for (int i = 1; i < columns.size(); ++i) {
            sbColumn.append(",");
            sbValue.append(",");
            sbColumn.append("`");
            sbColumn.append(columns.get(i));
            sbColumn.append("`");
            sbValue.append("?");
        }

        sbSql.append(sbColumn);
        sbSql.append(") VALUES (");
        sbSql.append(sbValue);
        sbSql.append(")");
        sql = sbSql.toString();
        insertSqlMap.put(key, sql);
        rs.setSql(sql);
        return rs;
    }

    /**
     * 由于分表的存在，sql不尽相同，所以不缓存sql。
     * @param t
     * @param tableName
     * @param ignore
     * @return
     */
    @Override
    public InsertResult insertSql(Object t, String tableName, boolean ignore) {
        Class<?> clazz = t.getClass();
        InsertResult rs = new InsertResult();
        boolean hasPKValue = SqlUtils.hasPKValue(t);
        rs.setHasPk(hasPKValue);
        String sql;
        StringBuffer sbSql = new StringBuffer("INSERT");
        if (ignore) {
            sbSql.append(" IGNORE");
        }
        sbSql.append(" INTO `");
        sbSql.append(tableName);
        sbSql.append("` (");
        List<String> columns = hasPKValue ? (SqlUtils.getAllColumns(clazz)) : (SqlUtils.getColumns(clazz));
        if (columns.isEmpty()) {
            throw new BadgerException(clazz.getName() + " not exist columns");
        }
        StringBuilder sbColumn = new StringBuilder();
        sbColumn.append("`").append(columns.get(0)).append("`");
        StringBuilder sbValue = new StringBuilder("?");
        for (int i = 1; i < columns.size(); ++i) {
            sbColumn.append(",");
            sbValue.append(",");
            sbColumn.append("`");
            sbColumn.append(columns.get(i));
            sbColumn.append("`");
            sbValue.append("?");
        }
        sbSql.append(sbColumn);
        sbSql.append(") VALUES (");
        sbSql.append(sbValue);
        sbSql.append(")");
        sql = sbSql.toString();
        rs.setSql(sql);
        return rs;
    }

    /**
     * 更加唯一主键查询
     * @param clazz
     * @return
     */
    @Override
    public String getSql(Class<?> clazz) {
        String sql = getSqlMap.get(clazz);
        if (sql != null) {
            return sql;
        }
        String idColumnName;
        List<String> idColumns = SqlUtils.getIdColumns(clazz);
        if (idColumns.size() != 1) {
            throw new MappingException("无法根据主键ID获取数据：主键不存在 或 有两个以上的主键");
        } else {
            idColumnName = idColumns.get(0);
        }
        StringBuilder sbSql = new StringBuilder("SELECT ");
        List<String> columns = SqlUtils.getAllColumns(clazz);
        if (columns.isEmpty()) {
            throw new BadgerException(clazz.getName() + " not exist columns");
        }
        StringBuilder sbColumn = new StringBuilder();
        sbColumn.append("`").append(columns.get(0)).append("`");
        for (int i = 1; i < columns.size(); ++i) {
            sbColumn.append(",`").append(columns.get(i)).append("`");
        }
        sbSql.append(sbColumn);
        sbSql.append(" FROM `");
        sbSql.append(SqlUtils.getTableInfo(clazz).getTableName());
        sbSql.append("` WHERE ");
        sbSql.append("`");
        sbSql.append(idColumnName);
        sbSql.append("`");
        sbSql.append("=?");
        sql = sbSql.toString();
        getSqlMap.put(clazz, sql);
        return sql;
    }

    /**
     * 由于分表的存在，不能缓存sql。
     * @param clazz
     * @param tableName
     * @return
     */
    @Override
    public String getSql(Class<?> clazz, String tableName) {
        String sql;
        String idColumnName;
        List<String> idColumns = SqlUtils.getIdColumns(clazz);
        if (idColumns.size() != 1) {
            throw new MappingException("无法根据主键ID获取数据：主键不存在 或 有两个以上的主键");
        } else {
            idColumnName = idColumns.get(0);
        }
        StringBuilder sbSql = new StringBuilder("SELECT ");
        List<String> columns = SqlUtils.getAllColumns(clazz);
        if (columns.isEmpty()) {
            throw new BadgerException(clazz.getName() + " not exist columns");
        }
        StringBuilder sbColumn = new StringBuilder();
        sbColumn.append("`").append(columns.get(0)).append("`");
        for (int i = 1; i < columns.size(); ++i) {
            sbColumn.append(", `").append(columns.get(i)).append("`");
        }
        sbSql.append(sbColumn);
        sbSql.append(" FROM `");
        sbSql.append(tableName);
        sbSql.append("` WHERE ");
        sbSql.append("`");
        sbSql.append(idColumnName);
        sbSql.append("`");
        sbSql.append("=?");
        sql = sbSql.toString();
        return sql;
    }

    /**
     * 根据主键删除
     * @param clazz
     * @return
     */
    @Override
    public String deleteEntitySql(Class<?> clazz) {
        String sql = deleteSqlMap.get(clazz);
        if (sql != null) {
            return sql;
        }
        String idColumnName;
        List<String> idColumns = SqlUtils.getIdColumns(clazz);
        if (idColumns.size() != 1) {
            throw new MappingException("无法根据主键删除：主键不存在 或 有两个以上的主键");
        } else {
            idColumnName = idColumns.get(0);
        }

        StringBuilder sbSql = new StringBuilder("DELETE FROM `");
        sbSql.append(SqlUtils.getTableInfo(clazz).getTableName());
        sbSql.append("` WHERE ");
        sbSql.append("`");
        sbSql.append(idColumnName);
        sbSql.append("`");
        sbSql.append("=?");
        sql = sbSql.toString();
        deleteSqlMap.put(clazz, sql);
        return sql;
    }

    /**
     * 根据主键删除，由于分表的存在则不能缓存sql。
     * @param clazz
     * @param tableName
     * @return
     */
    @Override
    public String deleteEntitySql(Class<?> clazz, String tableName) {
        String sql;
        String idColumnName;
        List<String> idColumns = SqlUtils.getIdColumns(clazz);
        if (idColumns.size() != 1) {
            throw new MappingException("无法根据主键删除：主键不存在 或 有两个以上的主键");
        } else {
            idColumnName = idColumns.get(0);
        }
        StringBuilder sbSql = new StringBuilder("DELETE FROM `");
        sbSql.append(tableName);
        sbSql.append("` WHERE ");
        sbSql.append("`");
        sbSql.append(idColumnName);
        sbSql.append("`");
        sbSql.append("=?");
        sql = sbSql.toString();
        return sql;
    }

    /**
     * 根据条件删除，由于条件有可能有很多，所以不缓存sql
     * @param clazz
     * @param condition
     * @return
     */
    @Override
    public String deleteConditionSql(Class<?> clazz, String condition) {
        StringBuilder sbSql = new StringBuilder("DELETE FROM `");
        sbSql.append(SqlUtils.getTableInfo(clazz).getTableName());
        sbSql.append("`");
        if (!Strings.isNullOrEmpty(condition)) {
            sbSql.append(" WHERE ").append(condition);
        }
        return sbSql.toString();
    }

    /**
     * 根据条件删除sql，由于条件和分表的存在，不缓存sql。
     * @param clazz
     * @param condition
     * @param tableName
     * @return
     */
    @Override
    public String deleteConditionSql(Class<?> clazz, String condition, String tableName) {
        StringBuilder sbSql = new StringBuilder("DELETE FROM `");
        sbSql.append(tableName);
        sbSql.append("`");
        if (!Strings.isNullOrEmpty(condition)) {
            sbSql.append(" WHERE ").append(condition);
        }
        return sbSql.toString();
    }

    /**
     * 根据id更新所有字段
     * @param clazz
     * @return
     */
    @Override
    public String updateEntitySql(Class<?> clazz) {
        String sql = updateSqlMap.get(clazz);
        if (sql != null) {
            return sql;
        }
        String idColumnName;
        List<String> idColumns = SqlUtils.getIdColumns(clazz);
        if (idColumns.size() != 1) {
            throw new MappingException("无法根据实体更新：主键不存在 或 有两个以上的主键");
        } else {
            idColumnName = idColumns.get(0);
        }

        StringBuilder sbSql = new StringBuilder("UPDATE `");
        sbSql.append(SqlUtils.getTableInfo(clazz).getTableName());
        sbSql.append("` SET ");
        List<String> columns = SqlUtils.getColumns(clazz);
        if (columns.isEmpty()) {
            throw new BadgerException(clazz.getName() + " not exist columns");
        }
        sbSql.append("`").append(columns.get(0)).append("`").append("=?");
        for (int i = 1; i < columns.size(); i++) {
            sbSql.append(",");
            sbSql.append("`");
            sbSql.append(columns.get(i));
            sbSql.append("`");
            sbSql.append("=?");
        }
        sbSql.append(" WHERE ");
        sbSql.append("`");
        sbSql.append(idColumnName);
        sbSql.append("`");
        sbSql.append("=?");
        sql = sbSql.toString();
        updateSqlMap.put(clazz, sql);
        return sql;
    }

    /**
     * 根据id更新，由于分表的存在不能缓存sql
     * @param clazz
     * @param tableName
     * @return
     */
    @Override
    public String updateEntitySql(Class<?> clazz, String tableName) {
        String idColumnName;
        List<String> idColumns = SqlUtils.getIdColumns(clazz);
        if (idColumns.size() != 1) {
            throw new MappingException("无法根据实体更新：主键不存在 或 有两个以上的主键");
        } else {
            idColumnName = idColumns.get(0);
        }

        StringBuilder sbSql = new StringBuilder("UPDATE `");
        sbSql.append(tableName);
        sbSql.append("` SET ");
        List<String> columns = SqlUtils.getColumns(clazz);
        if (columns.isEmpty()) {
            throw new BadgerException(clazz.getName() + " not exist columns");
        }
        sbSql.append("`").append(columns.get(0)).append("`").append("=?");
        for (int i = 1; i < columns.size(); i++) {
            sbSql.append(",");
            sbSql.append("`");
            sbSql.append(columns.get(i));
            sbSql.append("`");
            sbSql.append("=?");
        }
        sbSql.append(" WHERE ");
        sbSql.append("`");
        sbSql.append(idColumnName);
        sbSql.append("`");
        sbSql.append("=?");
        return sbSql.toString();
    }

    /**
     *
     * @param clazz
     * @param updateStatement
     * @param condition
     * @return
     */
    @Override
    public String updateSql(Class<?> clazz, String updateStatement, String condition) {
        StringBuilder sbSql = new StringBuilder("UPDATE `");
        sbSql.append(SqlUtils.getTableInfo(clazz).getTableName());
        sbSql.append("` SET ").append(updateStatement);
        if (!Strings.isNullOrEmpty(condition)) {
            sbSql.append(" WHERE ").append(condition);
        }
        return sbSql.toString();
    }

    /**
     *
     * @param clazz
     * @param updateStatement
     * @param condition
     * @param tableName
     * @return
     */
    @Override
    public String updateSql(Class<?> clazz, String updateStatement, String condition, String tableName) {
        StringBuilder sbSql = new StringBuilder("UPDATE `");
        sbSql.append(tableName);
        sbSql.append("` SET ").append(updateStatement);
        if (!Strings.isNullOrEmpty(condition)) {
            sbSql.append(" WHERE ").append(condition);
        }
        return sbSql.toString();
    }

    /**
     * 查询所有，谨慎使用
     * @param clazz
     * @return
     */
    @Override
    public String selectAllSql(Class<?> clazz) {
        String sql = selectSqlMap.get(clazz);
        if (sql != null) {
            return sql;
        }
        StringBuilder sbSql = new StringBuilder("SELECT ");
        List<String> columns = SqlUtils.getAllColumns(clazz);
        if (columns.isEmpty()) {
            throw new BadgerException(clazz.getName() + " not exist columns");
        }
        StringBuilder sbColumn = new StringBuilder();
        sbColumn.append("`").append(columns.get(0)).append("`");
        for (int i = 1; i < columns.size(); ++i) {
            sbColumn.append(",`").append(columns.get(i)).append("`");
        }
        sbSql.append(sbColumn);
        sbSql.append(" FROM `");
        sbSql.append(SqlUtils.getTableInfo(clazz).getTableName()).append("`");
        sql = sbSql.toString();
        selectSqlMap.put(clazz, sql);
        return sql;
    }

    /**
     * 查询所有谨慎使用
     * @param clazz
     * @param tableName
     * @return
     */
    @Override
    public String selectAllSql(Class<?> clazz, String tableName) {
        StringBuilder sbSql = new StringBuilder("SELECT ");
        List<String> columns = SqlUtils.getAllColumns(clazz);
        if (columns.isEmpty()) {
            throw new BadgerException(clazz.getName() + " not exist columns");
        }
        StringBuilder sbColumn = new StringBuilder();
        sbColumn.append("`").append(columns.get(0)).append("`");
        for (int i = 1; i < columns.size(); ++i) {
            sbColumn.append(",`").append(columns.get(i)).append("`");
        }
        sbSql.append(sbColumn);
        sbSql.append(" FROM `");
        sbSql.append(tableName).append("`");
        return sbSql.toString();
    }

    /**
     * 查询所有谨慎使用
     * @param clazz
     * @return
     */
    @Override
    public String selectSql(Class<?> clazz, String columns) {
        StringBuilder sbSql = new StringBuilder("SELECT ");
        sbSql.append(columns);
        sbSql.append(" FROM `");
        sbSql.append(SqlUtils.getTableInfo(clazz).getTableName()).append("`");
        return sbSql.toString();
    }

    /**
     * 查询所有谨慎使用
     * @param clazz
     * @param tableName
     * @return
     */
    @Override
    public String selectSql(Class<?> clazz, String columns, String tableName) {
        StringBuilder sbSql = new StringBuilder("SELECT ");
        sbSql.append(columns);
        sbSql.append(" FROM `");
        sbSql.append(tableName).append("`");
        return sbSql.toString();
    }

    /**
     * 计数
     * @param clazz
     * @return
     */
    @Override
    public String countSql(Class<?> clazz) {
        String sql = countSqlMap.get(clazz);
        if (sql != null) {
            return sql;
        }
        StringBuilder sbSql = new StringBuilder("SELECT COUNT(1) FROM `");
        sbSql.append(SqlUtils.getTableInfo(clazz).getTableName()).append("`");
        sql = sbSql.toString();
        countSqlMap.put(clazz, sql);
        return sql;
    }

    /**
     *
     * @param clazz
     * @param condition
     * @return
     */
    @Override
    public String countSql(Class<?> clazz, String condition) {
        if (Strings.isNullOrEmpty(condition)) {
            return countSql(clazz);
        }
        StringBuilder sbSql = new StringBuilder("SELECT COUNT(1) FROM `");
        sbSql.append(SqlUtils.getTableInfo(clazz).getTableName()).append("`");
        sbSql.append(" WHERE ").append(condition);
        return sbSql.toString();
    }

    /**
     * 根据条件计数
     * @param clazz
     * @param tableName
     * @param condition
     * @return
     */
    @Override
    public String countSql(Class<?> clazz, String tableName, String condition) {
        StringBuilder sbSql = new StringBuilder("SELECT COUNT(1) FROM `");
        sbSql.append(SqlUtils.getTableInfo(clazz).getTableName()).append("`");
        sbSql.append(" WHERE ").append(condition);
        return sbSql.toString();
    }

    /**
     * 分页查询
     * @param sql
     * @param start
     * @param max
     * @return
     */
    @Override
    public String getPageSql(String sql, int pageIndex, int pageSize) {
        StringBuilder sb = new StringBuilder(sql);
        sb.append(" LIMIT ").append(pageIndex).append(",").append(pageSize);
        return sb.toString();
    }
}
