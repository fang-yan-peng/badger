package org.jfaster.badger.test.entity;

import lombok.Getter;

/**
 *
 * @author yanpengfang
 * @create 2019-01-31 9:37 PM
 */
public enum TypeEnum {

    SELF("自营"), JOIN("加盟");

    @Getter
    private String desc;

    TypeEnum(String desc) {
        this.desc = desc;
    }
}
