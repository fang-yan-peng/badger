package org.jfaster.badger.sql.update;

import static org.jfaster.badger.jdbc.datasource.support.AbstractDataSourceFactory.DEFULT_NAME;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jfaster.badger.Badger;
import org.jfaster.badger.sql.JdbcHelper;
import org.jfaster.badger.util.CheckConditions;
import org.jfaster.badger.util.Strings;

/**
 *
 * @author yanpengfang
 * @create 2019-01-31 3:08 PM
 */
public class UpdateSqlStatementImpl implements UpdateSqlStatement {

    private List<Object> paramList;

    private Badger badger;

    private String sql;

    private String dbName;

    public UpdateSqlStatementImpl(String sql, Badger badger) {
        this.badger = badger;
        this.sql = sql;
    }

    @Override
    public UpdateSqlStatement addParam(Object obj) throws Exception {
        CheckConditions.checkNotNull(obj);
        initParamList();
        paramList.add(obj);
        return this;
    }

    @Override
    public UpdateSqlStatement addParam(Object... objs) throws Exception {
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
    public UpdateSqlStatement addParam(Collection<Object> objs) throws Exception {
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
    public UpdateSqlStatement setDataSourceName(String name) {
        CheckConditions.checkNotNull(name, "数据源名称不能为空");
        this.dbName = name;
        return this;
    }

    @Override
    public int execute() throws Exception {
        return JdbcHelper.executeUpdate(badger, sql,
                Strings.isNullOrEmpty(dbName) ? DEFULT_NAME : dbName,
                paramList);
    }

    private void initParamList() {
        if (paramList == null) {
            paramList = new ArrayList<>();
        }
    }
}
