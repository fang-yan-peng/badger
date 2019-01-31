package org.jfaster.badger.jdbc.type;

import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.jfaster.badger.jdbc.JdbcType;

public class BlobByteObjectArrayTypeHandler extends BaseTypeHandler<Byte[]> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, Byte[] parameter, JdbcType jdbcType)
      throws SQLException {
    ByteArrayInputStream bis = new ByteArrayInputStream(ByteArrayUtils.convertToPrimitiveArray(parameter));
    ps.setBinaryStream(i, bis, parameter.length);
  }

  @Override
  public Byte[] getNullableResult(ResultSet rs, int columnIndex)
      throws SQLException {
    Blob blob = rs.getBlob(columnIndex);
    return getBytes(blob);
  }

  private Byte[] getBytes(Blob blob) throws SQLException {
    Byte[] returnValue = null;
    if (blob != null) {
      returnValue = ByteArrayUtils.convertToObjectArray(blob.getBytes(1, (int) blob.length()));
    }
    return returnValue;
  }

  @Override
  public JdbcType getJdbcType() {
    return JdbcType.LONGVARBINARY;
  }

}
