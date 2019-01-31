package org.jfaster.badger.jdbc.datasource.support;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.sql.DataSource;

import lombok.Data;

/**
 * 主从数据源工厂
 * @author yanpengfang
 * @create 2019-01-16 2:15 PM
 */
@Data
public class MasterSlaveDataSourceFactory extends AbstractDataSourceFactory {

    private DataSource master;

    private List<DataSource> slaves;

    public MasterSlaveDataSourceFactory() {
    }

    public MasterSlaveDataSourceFactory(DataSource master, List<DataSource> slaves) {
        this.master = master;
        this.slaves = slaves;
    }

    public MasterSlaveDataSourceFactory(String name, DataSource master, List<DataSource> slaves) {
        super(name);
        this.master = master;
        this.slaves = slaves;
    }

    @Override
    public DataSource getMasterDataSource() {
        return master;
    }

    @Override
    public DataSource getSlaveDataSource() {
        return slaves.get(ThreadLocalRandom.current().nextInt(slaves.size()));
    }
}
