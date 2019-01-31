package org.jfaster.badger.jdbc.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.jfaster.badger.jdbc.JdbcType;

public class ByteObjectArrayTypeHandler extends BaseTypeHandler<Byte[]> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int index, Byte[] parameter, JdbcType jdbcType) throws SQLException {
    ps.setBytes(index, ByteArrayUtils.convertToPrimitiveArray(parameter));
  }

  @Override
  public Byte[] getNullableResult(ResultSet rs, int index) throws SQLException {
    byte[] bytes = rs.getBytes(index);
    return getBytes(bytes);
  }

  private Byte[] getBytes(byte[] bytes) {
    Byte[] returnValue = null;
    if (bytes != null) {
      returnValue = ByteArrayUtils.convertToObjectArray(bytes);
    }
    return returnValue;
  }

  @Override
  public JdbcType getJdbcType() {
    return JdbcType.LONGVARBINARY;
  }

}
