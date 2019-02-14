package org.jfaster.badger.jdbc.extractor.support;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.jfaster.badger.jdbc.extractor.ResultSetExtractor;
import org.jfaster.badger.jdbc.mapper.RowMapper;


public class ListResultSetExtractor<T> implements ResultSetExtractor<List<T>> {

  private final RowMapper<T> rowMapper;

  public ListResultSetExtractor(RowMapper<T> rowMapper) {
    this.rowMapper = rowMapper;
  }

  /**
   *
   * @param rs
   * @return
   * @throws SQLException
   */
  @Override
  public List<T> extractData(ResultSet rs) throws SQLException {
    List<T> results = new LinkedList<>();
    int rowNum = 0;
    while (rs.next()) {
      results.add(rowMapper.mapRow(rs, rowNum++));
    }
    return results;
  }

}
