package org.jfaster.badger.jdbc.type;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.jfaster.badger.jdbc.JdbcType;

/**
 */
public class BigDecimalTypeHandler extends BaseTypeHandler<BigDecimal> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int index, BigDecimal parameter, JdbcType jdbcType)
      throws SQLException {
    ps.setBigDecimal(index, parameter);
  }

  @Override
  public BigDecimal getNullableResult(ResultSet rs, int index)
      throws SQLException {
    return rs.getBigDecimal(index);
  }

  @Override
  public JdbcType getJdbcType() {
    return JdbcType.DECIMAL;
  }

}
