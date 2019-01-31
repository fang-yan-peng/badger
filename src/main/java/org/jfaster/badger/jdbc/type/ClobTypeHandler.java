package org.jfaster.badger.jdbc.type;

import java.io.StringReader;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.jfaster.badger.jdbc.JdbcType;

public class ClobTypeHandler extends BaseTypeHandler<String> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int index, String parameter, JdbcType jdbcType)
      throws SQLException {
    StringReader reader = new StringReader(parameter);
    ps.setCharacterStream(index, reader, parameter.length());
  }

  @Override
  public String getNullableResult(ResultSet rs, int index)
      throws SQLException {
    String value = "";
    Clob clob = rs.getClob(index);
    if (clob != null) {
      int size = (int) clob.length();
      value = clob.getSubString(1, size);
    }
    return value;
  }

  @Override
  public JdbcType getJdbcType() {
    return JdbcType.LONGVARCHAR;
  }
}
