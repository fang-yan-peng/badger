package org.jfaster.badger.sql;

import java.util.List;

import lombok.Data;

/**
 * sql 解析结果
 */
@Data
public class ParseResult {

    private String sql;

    private int shardParameterIndex = -1;

    private Object shardValue;

    private List<String> fields;

    private List<Object> values;

    private List<Object> sqlTree;

    private List<String> dynamicFields;
}
