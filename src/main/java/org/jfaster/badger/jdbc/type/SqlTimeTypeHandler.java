package org.jfaster.badger.jdbc.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

import org.jfaster.badger.jdbc.JdbcType;

public class SqlTimeTypeHandler extends BaseTypeHandler<Time> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int index, Time parameter, JdbcType jdbcType)
      throws SQLException {
    ps.setTime(index, parameter);
  }

  @Override
  public Time getNullableResult(ResultSet rs, int index)
      throws SQLException {
    return rs.getTime(index);
  }

  @Override
  public JdbcType getJdbcType() {
    return JdbcType.TIME;
  }

}
