package org.jfaster.badger.sql.delete;

import java.util.Collection;

public interface DeleteStatement {

    /*
     * 添加参数
     */
    DeleteStatement addParam(Object obj);

    /*
     * 添加参数
     */
    DeleteStatement addParam(Object... objs);

    /*
     * 添加参数
     */
    DeleteStatement addParam(Collection<Object> objs);

    /**
     * 手动设置分库分表字段
     */
    DeleteStatement setShardValue(Object shardValue);


    /**
     * 执行删除
     * @return
     * @throws Exception
     */
    int execute();

}
