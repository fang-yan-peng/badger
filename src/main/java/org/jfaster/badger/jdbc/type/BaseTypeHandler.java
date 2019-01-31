package org.jfaster.badger.jdbc.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.jfaster.badger.jdbc.JdbcType;

public abstract class BaseTypeHandler<T> implements TypeHandler<T> {
    @Override
    public void setParameter(PreparedStatement ps, int index, T parameter) throws SQLException {
        JdbcType jdbcType = getJdbcType();
        if (parameter == null) {
            try {
                ps.setNull(index, jdbcType.TYPE_CODE);
            } catch (SQLException e) {
                throw new TypeException("Error setting null for parameter #" + index + " with JdbcType " + jdbcType + ", " +
                        "try setting a different JdbcType for this parameter", e);
            }
        } else {
            try {
                setNonNullParameter(ps, index, parameter, jdbcType);
            } catch (Exception e) {
                throw new TypeException("Error setting non null for parameter #" + index + " with JdbcType " + jdbcType + ", " +
                        "try setting a different JdbcType for this parameter", e);
            }
        }
    }

    @Override
    public T getResult(ResultSet rs, int index) throws SQLException {
        T result;
        try {
            result = getNullableResult(rs, index);
        } catch (Exception e) {
            throw new TypeException("Error attempting to get column #" + index + " from result set", e);
        }
        if (rs.wasNull()) {
            return null;
        } else {
            return result;
        }
    }

    public abstract void setNonNullParameter(PreparedStatement ps, int index, T parameter, JdbcType jdbcType) throws SQLException;

    public abstract T getNullableResult(ResultSet rs, int index) throws SQLException;

    public abstract JdbcType getJdbcType();
}
