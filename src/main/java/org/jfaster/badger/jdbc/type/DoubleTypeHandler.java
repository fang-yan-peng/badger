package org.jfaster.badger.jdbc.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.jfaster.badger.jdbc.JdbcType;

public class DoubleTypeHandler extends BaseTypeHandler<Double> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int index, Double parameter, JdbcType jdbcType)
      throws SQLException {
    ps.setDouble(index, parameter);
  }

  @Override
  public Double getNullableResult(ResultSet rs, int index)
      throws SQLException {
    return rs.getDouble(index);
  }

  @Override
  public JdbcType getJdbcType() {
    return JdbcType.DOUBLE;
  }
}
