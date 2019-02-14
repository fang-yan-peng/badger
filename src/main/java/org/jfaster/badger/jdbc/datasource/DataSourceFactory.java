package org.jfaster.badger.jdbc.datasource;

import javax.sql.DataSource;

/**
 *
 * @author yanpengfang
 * create 2019-01-16 2:23 PM
 */
public interface DataSourceFactory {
    String getName();

    DataSource getMasterDataSource();

    DataSource getSlaveDataSource();
}
