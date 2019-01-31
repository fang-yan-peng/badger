package org.jfaster.badger.jdbc.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.jfaster.badger.jdbc.JdbcType;

public class DateTypeHandler extends BaseTypeHandler<Date> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int index, Date parameter, JdbcType jdbcType)
      throws SQLException {
    ps.setTimestamp(index, new Timestamp((parameter).getTime()));
  }

  @Override
  public Date getNullableResult(ResultSet rs, int index)
      throws SQLException {
    Timestamp sqlTimestamp = rs.getTimestamp(index);
    if (sqlTimestamp != null) {
      return new Date(sqlTimestamp.getTime());
    }
    return null;
  }

  @Override
  public JdbcType getJdbcType() {
    return JdbcType.TIMESTAMP;
  }
}
