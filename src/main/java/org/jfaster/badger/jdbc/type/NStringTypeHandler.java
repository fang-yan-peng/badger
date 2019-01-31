package org.jfaster.badger.jdbc.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.jfaster.badger.jdbc.JdbcType;

public class NStringTypeHandler extends BaseTypeHandler<String> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int index, String parameter, JdbcType jdbcType)
      throws SQLException {
    ps.setNString(index, parameter);
  }

  @Override
  public String getNullableResult(ResultSet rs, int index)
      throws SQLException {
    return rs.getNString(index);
  }

  @Override
  public JdbcType getJdbcType() {
    return JdbcType.NVARCHAR;
  }

}
