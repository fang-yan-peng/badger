package org.jfaster.badger.sql.select;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jfaster.badger.Badger;
import org.jfaster.badger.util.CheckConditions;
import org.jfaster.badger.util.Strings;

/**
 *
 * @author yanpengfang
 * create 2019-01-29 10:27 PM
 */
public class TypeQueryImpl<T, O> implements Query<O> {

    private String condition;

    private String columns;

    private List<Object> paramList;

    private Class<T> clazz;

    private Class<O> returnType;

    private int pageSize;

    private int pageIndex;

    private Object shardValue;

    private boolean useMaster;

    private Badger badger;

    public TypeQueryImpl(Class<T> clazz, Class<O> returnType, String condition, Badger badger) {
        this.condition = condition;
        this.clazz = clazz;
        this.returnType = returnType;
        this.badger = badger;
    }

    public TypeQueryImpl(Class<T> clazz, Class<O> returnType, String columns, String condition, Badger badger) {
        this.condition = condition;
        this.clazz = clazz;
        this.returnType = returnType;
        this.columns = columns;
        this.badger = badger;
    }

    @Override
    public Query<O> addParam(Object obj) {
        CheckConditions.checkNotNull(obj);
        initParamList();
        paramList.add(obj);
        return this;
    }

    @Override
    public Query<O> addParamIfNotNull(Object obj) {
        if (obj != null) {
            initParamList();
            paramList.add(obj);
        }
        return this;
    }

    @Override
    public Query<O> addParam(Object... objs) {
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
    public Query<O> addParam(Collection<Object> objs) {
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
    public Query<O> setShardValue(Object shardValue) {
        this.shardValue = shardValue;
        return this;
    }

    @Override
    public Query<O> userMaster() {
        this.useMaster = true;
        return this;
    }

    @Override
    public List<O> list() {
        if (Strings.isNullOrEmpty(columns)) {
            columns = "*";
        }
        if (pageSize > 0) {
            if (shardValue != null) {
                return JdbcSelectHelper.findByPage(clazz, columns, condition, paramList,
                        pageIndex, pageSize, shardValue, returnType, badger, useMaster);
            }
            return JdbcSelectHelper.findByPage(clazz, columns, condition, paramList,
                    pageIndex, pageSize, returnType, badger, useMaster);
        }
        if (shardValue != null) {
            return JdbcSelectHelper.find(clazz, columns, condition,
                    paramList, shardValue, returnType, badger, useMaster);
        }
        return JdbcSelectHelper.find(clazz, columns, condition,
                paramList, returnType, badger, useMaster);
    }

    @Override
    public long count() {
        if (shardValue != null) {
            return JdbcSelectHelper.count(clazz, condition, paramList, shardValue, badger, useMaster);
        }
        return JdbcSelectHelper.count(clazz, condition, paramList, badger, useMaster);
    }

    @Override
    public Query<O> setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    @Override
    public Query<O> setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
        return this;
    }

    @Override
    public O getOne() {
        List<O> res = this.list();
        if (res.isEmpty()) {
            return null;
        }
        return res.get(0);
    }

    private void initParamList() {
        if (paramList == null) {
            paramList = new ArrayList<>();
        }
    }
}
