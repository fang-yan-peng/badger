package org.jfaster.badger.test.entity;

import java.util.Date;

import org.jfaster.badger.jdbc.type.convert.EnumIntegerConverter;
import org.jfaster.badger.query.annotations.Column;
import org.jfaster.badger.query.annotations.Id;
import org.jfaster.badger.query.annotations.Table;

import lombok.Data;

/**
 * 司机
 * @author yanpengfang
 * @create 2019-01-31 9:27 PM
 */
@Data
@Table(tableName = "driver")
public class Driver {

    @Id
    @Column
    private int driverId;

    @Column
    private String driverName;

    @Column
    private int age;

    @Column(convert = EnumIntegerConverter.class)
    private TypeEnum type;

    @Column
    private Date createDate;

    @Column
    private Date updateDate;

}
