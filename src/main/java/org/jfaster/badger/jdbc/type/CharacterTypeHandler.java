package org.jfaster.badger.jdbc.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.jfaster.badger.jdbc.JdbcType;

public class CharacterTypeHandler extends BaseTypeHandler<Character> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int index, Character parameter, JdbcType jdbcType) throws SQLException {
    ps.setString(index, parameter.toString());
  }

  @Override
  public Character getNullableResult(ResultSet rs, int index) throws SQLException {
    String columnValue = rs.getString(index);
    if (columnValue != null) {
      return columnValue.charAt(0);
    } else {
      return null;
    }
  }

  @Override
  public JdbcType getJdbcType() {
    return JdbcType.VARCHAR;
  }
}
