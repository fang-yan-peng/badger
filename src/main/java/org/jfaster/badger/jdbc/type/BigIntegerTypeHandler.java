package org.jfaster.badger.jdbc.type;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.jfaster.badger.jdbc.JdbcType;

/**
 */
public class BigIntegerTypeHandler extends BaseTypeHandler<BigInteger> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int index, BigInteger parameter, JdbcType jdbcType) throws SQLException {
    ps.setBigDecimal(index, new BigDecimal(parameter));
  }

  @Override
  public BigInteger getNullableResult(ResultSet rs, int index) throws SQLException {
    BigDecimal bigDecimal = rs.getBigDecimal(index);
    return bigDecimal == null ? null : bigDecimal.toBigInteger();
  }

  @Override
  public JdbcType getJdbcType() {
    return JdbcType.DECIMAL;
  }

}
