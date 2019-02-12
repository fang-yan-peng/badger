package org.jfaster.badger.test.entity;

import org.jfaster.badger.query.annotations.Column;
import org.jfaster.badger.query.annotations.Extension;

import lombok.Data;

/**
 * 继承driver表结构
 * @author yanpengfang
 * @create 2019-02-11 10:10 PM
 */
@Data
@Extension(extend = Driver.class)
public class DriverExt {

    @Column(name = "avgAge")
    int avgAge;

    @Column
    int driverId;
}
