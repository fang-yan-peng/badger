package org.jfaster.badger.jdbc.type;

import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.jfaster.badger.jdbc.JdbcType;

public class BlobTypeHandler extends BaseTypeHandler<byte[]> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int index, byte[] parameter, JdbcType jdbcType)
      throws SQLException {
    ByteArrayInputStream bis = new ByteArrayInputStream(parameter);
    ps.setBinaryStream(index, bis, parameter.length);
  }

  @Override
  public byte[] getNullableResult(ResultSet rs, int index)
      throws SQLException {
    Blob blob = rs.getBlob(index);
    byte[] returnValue = null;
    if (null != blob) {
      returnValue = blob.getBytes(1, (int) blob.length());
    }
    return returnValue;
  }

  @Override
  public JdbcType getJdbcType() {
    return JdbcType.LONGVARBINARY;
  }

}