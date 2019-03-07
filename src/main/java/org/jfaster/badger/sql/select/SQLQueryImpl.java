package org.jfaster.badger.sql.select;

import static org.jfaster.badger.jdbc.datasource.support.AbstractDataSourceFactory.DEFULT_NAME;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jfaster.badger.Badger;
import org.jfaster.badger.util.CheckConditions;
import org.jfaster.badger.util.Strings;

/**
 * SQLQuery
 * @author yanpengfang
 * create 2019-01-30 11:54 PM
 */
public class SQLQueryImpl<T> implements SQLQuery<T> {

    private String dataSourceName;

    private String sql;

    private Badger badger;

    private List<Object> paramList;

    private Class<T> clazz;

    private boolean useMaster;

    public SQLQueryImpl(String sql, Badger badger, Class<T> clazz) {
        this.sql = sql;
        this.badger = badger;
        this.clazz = clazz;
    }

    @Override
    public SQLQuery<T> addParam(Object obj) {
        CheckConditions.checkNotNull(obj);
        initParamList();
        paramList.add(obj);
        return this;
    }

    @Override
    public SQLQuery<T> addParam(Object... objs) {
        if (objs != null && objs.length > 0) {
            initParamList();
            for (Object obj : objs) {
                CheckConditions.checkNotNull(obj);
                paramList.add(obj);
            }
        }
        return this;
    }

    @Override
    public SQLQuery<T> addParam(Collection<Object> objs) {
        if (objs != null && objs.size() > 0) {
            initParamList();
            for (Object obj : objs) {
                CheckConditions.checkNotNull(obj);
                paramList.add(obj);
            }
        }
        return this;
    }

    @Override
    public SQLQuery<T> setDataSourceName(String name) {
        CheckConditions.checkNotNull(name, "数据源名称不能为null");
        this.dataSourceName = name;
        return this;
    }

    @Override
    public SQLQuery<T> userMaster() {
        this.useMaster = true;
        return this;
    }

    @Override
    public List<T> list() {
        return JdbcSqlSelectHelper.find(clazz, sql,
                Strings.isNullOrEmpty(dataSourceName) ? DEFULT_NAME : dataSourceName,
                paramList, badger, useMaster);
    }

    @Override
    public T getOne() {
        return this.list().get(0);
    }

    private void initParamList() {
        if (paramList == null) {
            paramList = new ArrayList<>();
        }
    }
}
