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
public class QueryImpl<T> implements Query<T> {

    private String condition;

    private String columns;

    private List<Object> paramList;

    private Class<T> clazz;

    private int pageSize;

    private int pageIndex;

    private Object shardValue;

    private boolean useMaster;

    private Badger badger;

    private Condition logicCondition;

    public QueryImpl(Class<T> clazz, String condition, Badger badger) {
        this.condition = condition;
        this.clazz = clazz;
        this.badger = badger;
    }

    public QueryImpl(Class<T> clazz, String columns, String condition, Badger badger) {
        this.condition = condition;
        this.clazz = clazz;
        this.columns = columns;
        this.badger = badger;
    }

    public QueryImpl(Class<T> clazz, Condition condition, Badger badger) {
        this.logicCondition = condition;
        this.clazz = clazz;
        this.badger = badger;
    }

    public QueryImpl(Class<T> clazz, String columns, Condition condition, Badger badger) {
        this.logicCondition = condition;
        this.clazz = clazz;
        this.columns = columns;
        this.badger = badger;
    }

    @Override
    public Query<T> addParam(Object obj) {
        if (logicCondition != null) {
            return this;
        }
        CheckConditions.checkNotNull(obj);
        initParamList();
        paramList.add(obj);
        return this;
    }

    @Override
    public Query<T> addParamIfNotNull(Object obj) {
        if (logicCondition != null) {
            return this;
        }
        if (obj != null) {
            initParamList();
            paramList.add(obj);
        }
        return this;
    }

    @Override
    public Query<T> addParam(Object... objs) {
        if (logicCondition != null) {
            return this;
        }
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
    public Query<T> addParam(Collection<Object> objs) {
        if (logicCondition != null) {
            return this;
        }
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
    public Query<T> setShardValue(Object shardValue) {
        this.shardValue = shardValue;
        return this;
    }

    @Override
    public Query<T> userMaster() {
        this.useMaster = true;
        return this;
    }

    @Override
    public List<T> list() {
        if (Strings.isNullOrEmpty(columns)) {
            columns = "*";
        }
        if (logicCondition != null) {
            paramList = logicCondition.getParams();
            condition = logicCondition.getSql();
        }
        if (pageSize > 0) {
            if (shardValue != null) {
                return JdbcSelectHelper.findByPage(clazz, columns, condition, paramList,
                        pageIndex, pageSize, shardValue, badger, useMaster);
            }
            return JdbcSelectHelper.findByPage(clazz, columns, condition, paramList,
                    pageIndex, pageSize, badger, useMaster);
        }
        if (shardValue != null) {
            return JdbcSelectHelper.find(clazz, columns, condition,
                    paramList, shardValue, badger, useMaster);
        }
        return JdbcSelectHelper.find(clazz, columns, condition,
                paramList, badger, useMaster);
    }

    @Override
    public long count() {
        if (logicCondition != null) {
            paramList = logicCondition.getParams();
            condition = logicCondition.getSql();
        }
        if (shardValue != null) {
            return JdbcSelectHelper.count(clazz, condition, paramList, shardValue, badger, useMaster);
        }
        return JdbcSelectHelper.count(clazz, condition, paramList, badger, useMaster);
    }

    @Override
    public Query<T> setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    @Override
    public Query<T> setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
        return this;
    }

    @Override
    public T getOne() {
        List<T> res = this.list();
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
