package org.jfaster.badger.sql.delete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jfaster.badger.Badger;
import org.jfaster.badger.util.CheckConditions;

public class DeleteStatementImpl implements DeleteStatement {
    private Class<?> clazz;
    private String condition;
    private List<Object> paramList = null;
    private Badger badger;

    public DeleteStatementImpl(Class<?> clazz, String condition, Badger badger) {
        this.clazz = clazz;
        this.condition = condition;
        this.badger = badger;
    }

    private void initParamList() {
        if (paramList == null) {
            paramList = new ArrayList<>();
        }
    }

    @Override
    public DeleteStatement addParam(Object obj) {
        CheckConditions.checkNotNull(obj, "parameter can not be null");
        initParamList();
        paramList.add(obj);
        return this;
    }

    @Override
    public DeleteStatement addParam(Object... objs) {
        if (objs != null && objs.length > 0) {
            initParamList();
            for (Object obj : objs) {
                CheckConditions.checkNotNull(obj, "parameter can not be null");
                paramList.add(obj);
            }
        }
        return this;
    }

    @Override
    public DeleteStatement addParam(Collection<Object> objs) {
        if (objs != null && objs.size() > 0) {
            initParamList();
            for (Object obj : objs) {
                CheckConditions.checkNotNull(obj, "parameter can not be null");
                paramList.add(obj);
            }
        }
        return this;
    }

    @Override
    public int execute() {
        return JdbcDeleteHelper.deleteByCondition(clazz, condition, paramList, badger);
    }
}
