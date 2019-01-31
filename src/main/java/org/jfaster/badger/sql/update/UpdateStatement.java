package org.jfaster.badger.sql.update;

import java.util.Collection;

/**
 * 查询条件
 * @author yanpengfang
 * @create 2019-01-29 10:26 PM
 */
public interface UpdateStatement {
    /*
     * 添加参数
     */
    UpdateStatement addParam(Object obj) throws Exception;

    /*
     * 添加参数
     */
    UpdateStatement addParam(Object... objs) throws Exception;

    /*
     * 添加参数
     */
    UpdateStatement addParam(Collection<Object> objs) throws Exception;

    /**
     * 执行查询
     * @return
     */
    int execute() throws Exception;

}
