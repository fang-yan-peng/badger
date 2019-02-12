package org.jfaster.badger.transaction;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

/**

 */
public abstract class TransactionSynchronizationManager {

  private static final ThreadLocal<Map<DataSource, ConnectionHolder>> CONNECTION_HOLDERS =
          new ThreadLocal<>();

  public static void bindConnectionHolder(DataSource dataSource, ConnectionHolder connHolder) {
    Map<DataSource, ConnectionHolder> map = CONNECTION_HOLDERS.get();
    if (map == null) {
      map = new HashMap<>();
      CONNECTION_HOLDERS.set(map);
    }
    ConnectionHolder oldConnHolder = map.put(dataSource, connHolder);
    if (oldConnHolder != null) {
      throw new IllegalStateException("Already ConnectionHolder [" + oldConnHolder + "] for DataSource [" +
          dataSource + "] bound to thread [" + Thread.currentThread().getName() + "]");
    }
  }

  public static void unbindConnectionHolder(DataSource dataSource) {
    Map<DataSource, ConnectionHolder> map = CONNECTION_HOLDERS.get();
    if (map == null) {
      throw new IllegalStateException(
          "No value for DataSource [" + dataSource + "] bound to " +
              "thread [" + Thread.currentThread().getName() + "]");
    }
    ConnectionHolder connHolder = map.remove(dataSource);
    if (map.isEmpty()) {
      CONNECTION_HOLDERS.remove();
    }
    if (connHolder == null) {
      throw new IllegalStateException(
          "No value for DataSource [" + dataSource + "] bound to " +
              "thread [" + Thread.currentThread().getName() + "]");
    }
  }

  public static ConnectionHolder getConnectionHolder(DataSource dataSource) {
    Map<DataSource, ConnectionHolder> map = CONNECTION_HOLDERS.get();
    return map == null ? null : map.get(dataSource);
  }

}






