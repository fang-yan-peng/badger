package org.jfaster.badger.transaction;

import java.sql.Connection;

public class ConnectionHolder {

  private final Connection connection;

  private boolean rollbackOnly = false;

  public ConnectionHolder(Connection connection) {
    if (connection == null) {
      throw new IllegalArgumentException("connection can't be null");
    }
    this.connection = connection;
  }

  public Connection getConnection() {
    return connection;
  }

  public boolean isRollbackOnly() {
    return rollbackOnly;
  }

  public void setRollbackOnly(boolean rollbackOnly) {
    this.rollbackOnly = rollbackOnly;
  }
}
