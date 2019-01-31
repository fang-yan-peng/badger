package org.jfaster.badger.jdbc.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.jfaster.badger.jdbc.JdbcType;

public class ShortTypeHandler extends BaseTypeHandler<Short> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int index, Short parameter, JdbcType jdbcType)
      throws SQLException {
    ps.setShort(index, parameter);
  }

  @Override
  public Short getNullableResult(ResultSet rs, int index)
      throws SQLException {
    return rs.getShort(index);
  }

  @Override
  public JdbcType getJdbcType() {
    return JdbcType.SMALLINT;
  }

}
