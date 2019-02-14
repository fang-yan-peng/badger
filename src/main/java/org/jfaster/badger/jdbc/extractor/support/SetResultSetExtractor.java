package org.jfaster.badger.jdbc.extractor.support;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import org.jfaster.badger.jdbc.extractor.ResultSetExtractor;
import org.jfaster.badger.jdbc.mapper.RowMapper;
import org.jfaster.badger.jdbc.supplier.SetSupplier;

public class SetResultSetExtractor<T> implements ResultSetExtractor<Set<T>> {

  private final SetSupplier setSupplier;
  private final RowMapper<T> rowMapper;

  public SetResultSetExtractor(SetSupplier setSupplier, RowMapper<T> rowMapper) {
    this.setSupplier = setSupplier;
    this.rowMapper = rowMapper;
  }

  /**
   *
   * @param rs
   * @return
   * @throws SQLException
   */
  @Override
  public Set<T> extractData(ResultSet rs) throws SQLException {
    Set<T> results = setSupplier.get(rowMapper.getMappedClass());
    int rowNum = 0;
    while (rs.next()) {
      results.add(rowMapper.mapRow(rs, rowNum++));
    }
    return results;
  }

}
