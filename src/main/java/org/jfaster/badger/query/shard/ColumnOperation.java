package org.jfaster.badger.query.shard;

import lombok.Data;

/**
 *
 * @author yanpengfang
 * create 2019-01-04 9:03 PM
 */
@Data
public class ColumnOperation {

    private String column;

    private OperationType type;

    private Object[] value;

    public static enum OperationType {
        equ("="), unequ("!="), in("in"), notin("not in"), gte(">="), lte("<="), gt(">"), lt("<"), isnull("is null"), notnull("is not null");

        public String op;

        private OperationType(String op) {
            this.op = op;
        }

        public String getOp() {
            return this.op;
        }
    }
}
