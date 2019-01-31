package org.jfaster.badger.query.sql;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * sql树 存储表达式信息
 * @author yanpengfang
 * @create 2019-01-03 10:44 AM
 */
@Data
public class SqlTree {
    private String fieldName;//参数字段
    private String option;//参数符号
    private int level;//参数级别
    private int count;//参数数量
    private List<Object> values = new ArrayList<>();

    public SqlTree(){

    }

    public SqlTree(String fieldName,String option,int level,int count){
        this.fieldName = fieldName;
        this.option = option;
        this.level = level;
        this.count = count;
    }
}
