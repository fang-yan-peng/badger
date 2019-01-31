package org.jfaster.badger.jdbc.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.jfaster.badger.jdbc.JdbcType;

public class DateOnlyTypeHandler extends BaseTypeHandler<Date> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int index, Date parameter, JdbcType jdbcType)
      throws SQLException {
    ps.setDate(index, new java.sql.Date((parameter.getTime())));
  }

  @Override
  public Date getNullableResult(ResultSet rs, int index)
      throws SQLException {
    java.sql.Date sqlDate = rs.getDate(index);
    if (sqlDate != null) {
      return new Date(sqlDate.getTime());
    }
    return null;
  }

  @Override
  public JdbcType getJdbcType() {
    return JdbcType.DATE;
  }
}
