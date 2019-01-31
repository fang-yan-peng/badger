package org.jfaster.badger.jdbc.type;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.jfaster.badger.jdbc.JdbcType;

public class SqlDateTypeHandler extends BaseTypeHandler<Date> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int index, Date parameter, JdbcType jdbcType)
      throws SQLException {
    ps.setDate(index, parameter);
  }

  @Override
  public Date getNullableResult(ResultSet rs, int index)
      throws SQLException {
    return rs.getDate(index);
  }

  @Override
  public JdbcType getJdbcType() {
    return JdbcType.DATE;
  }

}
