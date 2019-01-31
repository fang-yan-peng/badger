package org.jfaster.badger.jdbc.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author yanpengfang
 * @create 2019-01-07 3:48 PM
 */
public interface RowMapper<T> {
    T mapRow(ResultSet rs, int rowNum) throws SQLException;

    Class<T> getMappedClass();
}
