package org.jfaster.badger.dialect;

import java.util.List;

import lombok.Data;

/**
 *
 * @author yanpengfang
 * create 2019-01-12 12:14 PM
 */
@Data
public class InsertResult {
    private String sql;
    private boolean hasPk;
    private List<Object> values;
}
