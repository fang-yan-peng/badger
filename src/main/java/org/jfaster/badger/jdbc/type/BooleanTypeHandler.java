package org.jfaster.badger.jdbc.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.jfaster.badger.jdbc.JdbcType;

public class BooleanTypeHandler extends BaseTypeHandler<Boolean> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int index, Boolean parameter, JdbcType jdbcType)
      throws SQLException {
    ps.setBoolean(index, parameter);
  }

  @Override
  public Boolean getNullableResult(ResultSet rs, int index)
      throws SQLException {
    return rs.getBoolean(index);
  }

  @Override
  public JdbcType getJdbcType() {
    return JdbcType.BOOLEAN;
  }
}
