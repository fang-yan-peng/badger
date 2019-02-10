package org.jfaster.badger.test.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.jfaster.badger.query.annotations.Column;
import org.jfaster.badger.query.annotations.Id;
import org.jfaster.badger.query.annotations.ShardColumn;
import org.jfaster.badger.query.annotations.ShardTable;
import org.jfaster.badger.query.shard.TableShardStrategy;

import lombok.Data;

/**
 * 只分表不分库
 * @author yanpengfang
 * @create 2019-01-31 9:41 PM
 */
@ShardTable(tables = {"driver_order_0", "driver_order_1"},
        tableShardStrategy = Order.OrderTableShardStrategy.class
        /*,dbShardStrategy = Order.OrderDataSourceShardStrategy.class*/)
@Data
public class Order {

    @Id
    @Column
    private String orderNo;

    @Column
    private BigDecimal money;

    @ShardColumn
    @Column
    private int driverId;

    @Column
    private Date createDate;

    @Column
    private Date updateDate;

    public static class OrderTableShardStrategy implements TableShardStrategy<Integer> {
        @Override
        public String shardSingle(List<String> tables, Integer shardValue) {
            int mod = shardValue % 2;
            for (String table : tables) {
                if (table.endsWith("_" + mod)) {
                    return table;
                }
            }
            return tables.get(0);
        }
    }

    /*public static class OrderDataSourceShardStrategy implements DataSourceShardStrategy<Integer> {
        @Override
        public String shardSingle(Integer shardValue) {
            int mod = shardValue % 2;
            return "db_" + mod;
        }
    }*/
}
