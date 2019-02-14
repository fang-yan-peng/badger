package org.jfaster.badger.jdbc.datasource.support;

import javax.sql.DataSource;

import lombok.Data;

/**
 * 单一数据源
 * @author yanpengfang
 * create 2019-01-16 2:30 PM
 */
@Data
public class SingleDataSourceFactory extends AbstractDataSourceFactory {

    private DataSource dataSource;

    public SingleDataSourceFactory() {
    }

    public SingleDataSourceFactory(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public SingleDataSourceFactory(String name, DataSource dataSource) {
        super(name);
        this.dataSource = dataSource;
    }

    @Override
    public DataSource getMasterDataSource() {
        return dataSource;
    }

    @Override
    public DataSource getSlaveDataSource() {
        return dataSource;
    }
}
