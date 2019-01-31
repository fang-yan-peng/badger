package org.jfaster.badger.jdbc.mapper.support;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jfaster.badger.jdbc.ResultSetWrapper;
import org.jfaster.badger.jdbc.mapper.RowMapper;
import org.jfaster.badger.jdbc.type.TypeHandler;
import org.jfaster.badger.jdbc.type.TypeHandlerRegistry;

/**
 * 单列RowMapper
 * @author yanpengfang
 * @create 2019-01-07 3:48 PM
 */
public class SingleColumnRowMapper<T> implements RowMapper<T> {

  private Class<T> mappedClass;

  public SingleColumnRowMapper(Class<T> mappedClass) {
    this.mappedClass = mappedClass;
  }

  @SuppressWarnings("unchecked")
  public T mapRow(ResultSet rs, int rowNum) throws SQLException {
    ResultSetWrapper rsw = new ResultSetWrapper(rs);
    int index = 1;
    TypeHandler<?> typeHandler = TypeHandlerRegistry.getTypeHandler(mappedClass, rsw.getJdbcType(index));
    Object value = typeHandler.getResult(rsw.getResultSet(), index);
    return (T) value;
  }

  @Override
  public Class<T> getMappedClass() {
    return mappedClass;
  }

}
