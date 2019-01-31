package org.jfaster.badger.sql.update;

import java.util.Collection;

/**
 * 查询条件
 * @author yanpengfang
 * @create 2019-01-29 10:26 PM
 */
public interface UpdateSqlStatement {
    /*
     * 添加参数
     */
    UpdateSqlStatement addParam(Object obj) throws Exception;

    /*
     * 添加参数
     */
    UpdateSqlStatement addParam(Object... objs) throws Exception;

    /*
     * 添加参数
     */
    UpdateSqlStatement addParam(Collection<Object> objs) throws Exception;

    /**
     * 设置数据源名
     * @param name
     * @return
     * @throws Exception
     */
    UpdateSqlStatement setDataSourceName(String name);

    /**
     * 执行查询
     * @return
     */
    int execute() throws Exception;

}
