package org.jfaster.badger.jdbc.datasource.support;

import org.jfaster.badger.jdbc.datasource.DataSourceFactory;

/**
 *
 * @author yanpengfang
 * create 2019-01-16 2:24 PM
 */
public abstract class AbstractDataSourceFactory implements DataSourceFactory {

    public final static String DEFULT_NAME = "DEFAULT";

    private String name;

    protected AbstractDataSourceFactory() {
        this(DEFULT_NAME);
    }

    protected AbstractDataSourceFactory(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
