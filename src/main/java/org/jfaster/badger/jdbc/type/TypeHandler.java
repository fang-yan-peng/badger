package org.jfaster.badger.jdbc.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface TypeHandler<T> {
    void setParameter(PreparedStatement ps, int index, T parameter) throws SQLException;

    T getResult(ResultSet rs, int index) throws SQLException;
}
