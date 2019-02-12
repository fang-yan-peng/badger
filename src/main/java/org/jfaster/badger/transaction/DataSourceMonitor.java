package org.jfaster.badger.transaction;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.sql.DataSource;

/**
 * 监控datasource
 *
 */
public class DataSourceMonitor {

  private static final ConcurrentHashMap<DataSource, AtomicInteger> map =
      new ConcurrentHashMap<DataSource, AtomicInteger>();

  private static volatile boolean forceCheckAutoCommit = false;

  public static boolean needCheckAutoCommit(DataSource ds) {
    return forceCheckAutoCommit || map.get(ds) != null;
  }

  public static void resetAutoCommitFail(DataSource ds) {
    AtomicInteger val = map.get(ds);
    if (val == null) {
      val = new AtomicInteger();
      AtomicInteger old = map.putIfAbsent(ds, val);
      if (old != null) {
        val = old;
      }
    }
    val.incrementAndGet();
  }

  public static void setForceCheckAutoCommit(boolean forceCheckAutoCommit) {
    DataSourceMonitor.forceCheckAutoCommit = forceCheckAutoCommit;
  }

  public static Map<DataSource, Integer> getFailedDataSources() {
    Map<DataSource, Integer> dsMap = new HashMap<DataSource, Integer>();
    for (Map.Entry<DataSource, AtomicInteger> entry : map.entrySet()) {
      dsMap.put(entry.getKey(), entry.getValue().intValue());
    }
    return dsMap;
  }

}
