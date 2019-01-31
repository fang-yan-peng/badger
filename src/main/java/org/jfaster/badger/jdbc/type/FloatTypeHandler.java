package org.jfaster.badger.jdbc.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.jfaster.badger.jdbc.JdbcType;

public class FloatTypeHandler extends BaseTypeHandler<Float> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int index, Float parameter, JdbcType jdbcType)
      throws SQLException {
    ps.setFloat(index, parameter);
  }

  @Override
  public Float getNullableResult(ResultSet rs, int index)
      throws SQLException {
    return rs.getFloat(index);
  }

  @Override
  public JdbcType getJdbcType() {
    return JdbcType.FLOAT;
  }

}
