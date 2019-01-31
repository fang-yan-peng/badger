package org.jfaster.badger.jdbc.extractor.support;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jfaster.badger.jdbc.extractor.ResultSetExtractor;
import org.jfaster.badger.jdbc.mapper.RowMapper;
import org.jfaster.badger.util.Arrays;

public class ArrayResultSetExtractor<T> implements ResultSetExtractor<Object> {

  private final RowMapper<T> rowMapper;

  public ArrayResultSetExtractor(RowMapper<T> rowMapper) {
    this.rowMapper = rowMapper;
  }

  @Override
  public Object extractData(ResultSet rs) throws SQLException {
    List<T> list = new ArrayList<>();
    int rowNum = 0;
    while (rs.next()) {
      list.add(rowMapper.mapRow(rs, rowNum++));
    }
    return Arrays.toArray(list, rowMapper.getMappedClass());
  }

}
