package org.jfaster.badger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.sql.DataSource;

import org.jfaster.badger.exception.BadgerException;
import org.jfaster.badger.jdbc.datasource.DataSourceFactory;
import org.jfaster.badger.jdbc.datasource.support.SingleDataSourceFactory;
import org.jfaster.badger.sql.delete.DeleteStatement;
import org.jfaster.badger.sql.delete.DeleteStatementImpl;
import org.jfaster.badger.sql.delete.JdbcDeleteHelper;
import org.jfaster.badger.sql.get.JdbcGetHelper;
import org.jfaster.badger.sql.insert.JdbcInsertHelper;
import org.jfaster.badger.sql.interceptor.SqlInterceptor;
import org.jfaster.badger.sql.select.Query;
import org.jfaster.badger.sql.select.QueryImpl;
import org.jfaster.badger.sql.select.SQLQuery;
import org.jfaster.badger.sql.select.SQLQueryImpl;
import org.jfaster.badger.sql.update.JdbcUpdateHelper;
import org.jfaster.badger.sql.update.UpdateSqlStatement;
import org.jfaster.badger.sql.update.UpdateSqlStatementImpl;
import org.jfaster.badger.sql.update.UpdateStatement;
import org.jfaster.badger.sql.update.UpdateStatementImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * badger 入口
 * @author yanpengfang
 * @create 2019-01-11 9:08 PM
 */
public class Badger extends Config {

    private static Logger logger = LoggerFactory.getLogger(Badger.class);

    /*****数据源相关*****/
    private Map<String, DataSourceFactory> dataSourceFactories = new HashMap<>();

    public void setDataSource(DataSource datasource) {
        DataSourceFactory factory = new SingleDataSourceFactory(datasource);
        dataSourceFactories.put(factory.getName(), factory);
    }

    public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
        dataSourceFactories.put(dataSourceFactory.getName(), dataSourceFactory);
    }

    public void setDataSourceFactories(List<DataSourceFactory> factories) {
        for (DataSourceFactory factory : factories) {
            dataSourceFactories.put(factory.getName(), factory);
        }
    }

    public DataSource getMasterDataSource(String name) {
        DataSourceFactory factory = dataSourceFactories.get(name);
        if (factory == null) {
            throw new BadgerException("dataSource name:%s is not register", name);
        }
        return factory.getMasterDataSource();
    }

    public DataSource getSlaveDataSource(String name) {
        DataSourceFactory factory = dataSourceFactories.get(name);
        if (factory == null) {
            throw new BadgerException("dataSource name:%s is not register", name);
        }
        return factory.getSlaveDataSource();
    }

    /*********拦截器相关*********/
    private SqlInterceptor interceptor;

    public void setInterceptor(SqlInterceptor interceptor) {
        this.interceptor = interceptor;
    }

    public SqlInterceptor getInterceptor() {
        return this.interceptor;
    }

    /*************实例化相关*****************/
    public static Badger newInstance(DataSource dataSource) {
        Badger badger = newInstance();
        badger.setDataSource(dataSource);
        return badger;
    }

    public static Badger newInstance(DataSourceFactory dataSourceFactory) {
        Badger badger = newInstance();
        badger.setDataSourceFactory(dataSourceFactory);
        return badger;
    }

    public static Badger newInstance(DataSourceFactory... dataSourceFactories) {
        return newInstance(Arrays.asList(dataSourceFactories));
    }

    public static Badger newInstance(List<DataSourceFactory> dataSourceFactories) {
        Badger badger = newInstance();
        badger.setDataSourceFactories(dataSourceFactories);
        return badger;
    }

    private final static CopyOnWriteArrayList<Badger> instances = new CopyOnWriteArrayList<>();

    private Badger() {

    }

    public synchronized static Badger newInstance() {
        if (instances.size() == 1) {
            if (logger.isWarnEnabled()) {
                logger.warn("Find out more badger instances, it is recommended to use only one");
            }
        }
        Badger badger = new Badger();
        instances.add(badger);
        return badger;
    }

    public static List<Badger> getInstances() {
        List<Badger> badgers = new ArrayList<>(instances);
        return Collections.unmodifiableList(badgers);
    }

    /**********************************db 操作**************************************/

    public <T> int save(T t) {
        return JdbcInsertHelper.insert(t, false, this);
    }

    public <T> int saveIgnore(T t) {
        return JdbcInsertHelper.insert(t, true, this);
    }

    public <T> int saveNotNull(T t) {
        return JdbcInsertHelper.insertNotNull(t, false, this);
    }

    public <T> int saveNotNullIgnore(T t) {
        return JdbcInsertHelper.insertNotNull(t, true, this);
    }

    public <T> int delete(Class<T> clazz, Object id) {
        return JdbcDeleteHelper.deleteEntity(clazz, id, this);
    }

    public <T> DeleteStatement createDeteleStatement(Class<T> clazz, String condition) {
        return new DeleteStatementImpl(clazz, condition, this);
    }

    public <T> int update(T t) {
        return JdbcUpdateHelper.updateEntity(t, this);
    }

    public <T> UpdateStatement createUpdateStatement(Class<T> clazz, String updateStatement, String condition) {
        return new UpdateStatementImpl<>(clazz, updateStatement, condition, this);
    }

    public UpdateSqlStatement createUpdateSqlStatement(String sql) {
        return new UpdateSqlStatementImpl(sql, this);
    }

    public <T> T get(Class<T> clazz, Object id) {
        return JdbcGetHelper.get(clazz, id, this, false);
    }

    public <T> T get(Class<T> clazz, Object id, boolean useMaster) {
        return JdbcGetHelper.get(clazz, id, this, useMaster);
    }

    public <T> Query<T> createQuery(Class<T> clazz, String columns, String condition) {
        return new QueryImpl<>(clazz, columns, condition, this);
    }

    public <T> Query<T> createQuery(Class<T> clazz, String condition) {
        return new QueryImpl<>(clazz, condition, this);
    }

    public <T> SQLQuery<T> createSqlQuery(Class<T> clazz, String sql) {
        return new SQLQueryImpl<>(sql, this, clazz);
    }

    /**
     * todo
     * 动态刷新数据源
     */
    public void refreshDataSourceFactory() {

    }
}
