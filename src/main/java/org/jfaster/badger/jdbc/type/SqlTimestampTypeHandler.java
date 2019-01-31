package org.jfaster.badger.jdbc.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.jfaster.badger.jdbc.JdbcType;

public class SqlTimestampTypeHandler extends BaseTypeHandler<Timestamp> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int index, Timestamp parameter, JdbcType jdbcType)
      throws SQLException {
    ps.setTimestamp(index, parameter);
  }

  @Override
  public Timestamp getNullableResult(ResultSet rs, int index)
      throws SQLException {
    return rs.getTimestamp(index);
  }

  @Override
  public JdbcType getJdbcType() {
    return JdbcType.TIMESTAMP;
  }

}
