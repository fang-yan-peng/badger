package org.jfaster.badger.sql.select;

/**
 *
 * @author yanpengfang
 * create 2019-04-24 11:47 AM
 */
public class OrderByColumn {

    private String column;

    private String order;

    private static final String DESC = "desc";

    private static final String ASC = "asc";

    private static final String FMT = "%s %s";

    private OrderByColumn(String column, String order) {
        this.column = column;
        this.order = order;
    }

    public static OrderByColumn desc(String column) {
        return new OrderByColumn(column, DESC);
    }

    public static OrderByColumn asc(String column) {
        return new OrderByColumn(column, ASC);
    }

    public String orderColumn() {
        return String.format(FMT, column, order);
    }
}
