package org.jfaster.badger;

import lombok.Data;

/**
 * badger dao 配置相关
 * @author yanpengfang
 * @create 2019-01-02 9:36 PM
 */
@Data
public class Config {

    /********badger设置相关*********/

    /**
     * 设置数据库方言
     */
    private String dialect = "mysql";

    private String listSupplier = "arrayList";

    private String setSupplier = "hashSet";

    /**
     * 查询超时
     */
    private int queryTimeout = 5;

    /**
     * 更新超时时间
     */
    private int updateTimeout = 10;

    /**
     * 设置缓存sql的大小
     */
    private int cacheSqlLimit = 10000;

    /**
     * 设置分页数据的每页大小
     */
    private int pageSizeLimit = 200;

    /**
     * 设置是否使用spring的事物管理器
     */
    private String transactionManager = "badger";
}
