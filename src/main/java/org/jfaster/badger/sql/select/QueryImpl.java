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
 * @create 2019-01-29 10:27 PM
 */
public class QueryImpl<T> implements Query<T> {

    private String condition;

    private String columns;

    private List<Object> paramList;

    private Class<T> clazz;

    private int pageSize;

    private int pageIndex;

    private boolean useMaster;

    private Badger badger;

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

    @Override
    public Query addParam(Object obj) throws Exception {
        CheckConditions.checkNotNull(obj);
        initParamList();
        paramList.add(obj);
        return this;
    }

    @Override
    public Query addParam(Object... objs) throws Exception {
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
    public Query addParam(Collection<Object> objs) throws Exception {
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
    public Query userMaster() {
        this.useMaster = true;
        return this;
    }

    @Override
    public List<T> list() throws Exception {
        if (Strings.isNullOrEmpty(columns)) {
            columns = "*";
        }
        if (pageSize > 0) {
            return JdbcSelectHelper.findByPage(clazz, columns, condition, paramList,
                    pageIndex, pageSize, badger, useMaster);
        } else {
            return JdbcSelectHelper.find(clazz, columns, condition,
                    paramList, badger, useMaster);
        }
    }

    @Override
    public long count() throws Exception {
        return JdbcSelectHelper.count(clazz, condition, paramList, badger, useMaster);
    }

    @Override
    public Query setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    @Override
    public Query setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
        return this;
    }

    private void initParamList() {
        if (paramList == null) {
            paramList = new ArrayList<>();
        }
    }
}
