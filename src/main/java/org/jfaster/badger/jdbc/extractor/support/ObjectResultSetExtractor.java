package org.jfaster.badger.jdbc.extractor.support;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jfaster.badger.exception.BadgerException;
import org.jfaster.badger.jdbc.extractor.ResultSetExtractor;
import org.jfaster.badger.jdbc.mapper.RowMapper;


public class ObjectResultSetExtractor<T> implements ResultSetExtractor<T> {

  private final RowMapper<T> rowMapper;

  public ObjectResultSetExtractor(RowMapper<T> rowMapper) {
    this.rowMapper = rowMapper;
  }

  @Override
  public T extractData(ResultSet rs) throws SQLException {
    Class<T> mappedClass = rowMapper.getMappedClass();
    if (!mappedClass.isPrimitive()) {
      return rs.next() ? rowMapper.mapRow(rs, 0) : null;
    }

    // 原生类型
    if (!rs.next()) {
      throw new BadgerException("no data, can't cast null to primitive type " + mappedClass);
    }
    T r = rowMapper.mapRow(rs, 0);
    if (r == null) {
      throw new BadgerException("data is null, can't cast null to primitive type " + mappedClass);
    }
    return r;
  }

}
