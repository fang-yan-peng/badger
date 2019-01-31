package org.jfaster.badger.jdbc.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.jfaster.badger.jdbc.JdbcType;

public class LongTypeHandler extends BaseTypeHandler<Long> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int index, Long parameter, JdbcType jdbcType)
      throws SQLException {
    ps.setLong(index, parameter);
  }

  @Override
  public Long getNullableResult(ResultSet rs, int index)
      throws SQLException {
    return rs.getLong(index);
  }

  @Override
  public JdbcType getJdbcType() {
    return JdbcType.BIGINT;
  }

}
