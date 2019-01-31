package org.jfaster.badger.jdbc.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.jfaster.badger.jdbc.JdbcType;

public class ByteArrayTypeHandler extends BaseTypeHandler<byte[]> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int index, byte[] parameter, JdbcType jdbcType)
      throws SQLException {
    ps.setBytes(index, parameter);
  }

  @Override
  public byte[] getNullableResult(ResultSet rs, int index)
      throws SQLException {
    return rs.getBytes(index);
  }

  @Override
  public JdbcType getJdbcType() {
    return JdbcType.LONGVARBINARY;
  }
}
