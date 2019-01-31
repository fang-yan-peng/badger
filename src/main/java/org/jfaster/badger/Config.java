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

    private String dialect = "mysql";

    private String listSupplier = "arrayList";

    private String setSupplier = "hashSet";

    private int queryTimeout = 5;

    private int updateTimeout = 10;

    private boolean compatibleWithEmptyCollection = true;

    private boolean checkColumn = false;

    private int pageSizeLimit = 200;
}
