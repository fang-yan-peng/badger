package org.jfaster.badger.sql.update;

import java.util.Collection;

/**
 * 查询条件
 * @author yanpengfang
 * create 2019-01-29 10:26 PM
 */
public interface UpdateStatement {
    /*
     * 添加参数
     */
    UpdateStatement addParam(Object obj);

    /*
     * 添加参数
     */
    UpdateStatement addParamIfNotNull(Object obj);

    /*
     * 添加参数
     */
    UpdateStatement addParam(Object... objs);

    /*
     * 添加参数
     */
    UpdateStatement addParam(Collection<Object> objs);

    /**
     * 手动设置分库分表字段
     */
    UpdateStatement setShardValue(Object shardValue);

    /**
     * 执行查询
     * @return
     */
    int execute();

}
