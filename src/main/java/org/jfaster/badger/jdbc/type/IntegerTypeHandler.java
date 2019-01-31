package org.jfaster.badger.jdbc.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.jfaster.badger.jdbc.JdbcType;

public class IntegerTypeHandler extends BaseTypeHandler<Integer> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int index, Integer parameter, JdbcType jdbcType)
      throws SQLException {
    ps.setInt(index, parameter);
  }

  @Override
  public Integer getNullableResult(ResultSet rs, int index)
      throws SQLException {
    return rs.getInt(index);
  }

  @Override
  public JdbcType getJdbcType() {
    return JdbcType.INTEGER;
  }
}
