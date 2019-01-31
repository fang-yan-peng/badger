package org.jfaster.badger.jdbc.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.jfaster.badger.jdbc.JdbcType;

public class ByteTypeHandler extends BaseTypeHandler<Byte> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int index, Byte parameter, JdbcType jdbcType)
      throws SQLException {
    ps.setByte(index, parameter);
  }

  @Override
  public Byte getNullableResult(ResultSet rs, int index)
      throws SQLException {
    return rs.getByte(index);
  }

  @Override
  public JdbcType getJdbcType() {
    return JdbcType.TINYINT;
  }
}
