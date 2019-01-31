package org.jfaster.badger.test.bean;

import lombok.Data;

/**
 *
 * @author yanpengfang
 * @create 2019-01-09 3:52 PM
 */
@Data
public class ChildBean extends ParentBean{

    private String childName;
    private int childAge;
    private boolean man;
    private boolean isWoman;
    private boolean isceo;
}
