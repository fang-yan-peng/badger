package org.jfaster.badger.jdbc;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yanpengfang
 * create 2019-01-07 2:37 PM
 */
public class ResultSetWrapper {

  private final ResultSet resultSet;

  private final List<String> columnNames = new ArrayList<String>();
  private final List<JdbcType> jdbcTypes = new ArrayList<JdbcType>();

  public ResultSetWrapper(ResultSet rs) throws SQLException {
    this.resultSet = rs;
    final ResultSetMetaData metaData = rs.getMetaData();
    final int columnCount = metaData.getColumnCount();
    for (int index = 1; index <= columnCount; index++) {
      String columnName = metaData.getColumnLabel(index);
      if (columnName == null || columnName.length() < 1) {
        columnName = metaData.getColumnName(index);
      }
      columnNames.add(columnName.trim());
      jdbcTypes.add(JdbcType.forCode(metaData.getColumnType(index)));
    }
  }

  public int getColumnCount() {
    return columnNames.size();
  }

  public String getColumnName(int index) {
    return columnNames.get(index - 1);
  }

  public JdbcType getJdbcType(int index) {
    return jdbcTypes.get(index - 1);
  }

  public ResultSet getResultSet() {
    return resultSet;
  }

}
