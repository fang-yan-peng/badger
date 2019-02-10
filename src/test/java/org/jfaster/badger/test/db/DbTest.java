package org.jfaster.badger.test.db;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.jfaster.badger.Badger;
import org.jfaster.badger.sql.delete.DeleteStatement;
import org.jfaster.badger.sql.select.Query;
import org.jfaster.badger.sql.update.UpdateStatement;
import org.jfaster.badger.test.entity.Driver;
import org.jfaster.badger.test.entity.Order;
import org.jfaster.badger.test.entity.TypeEnum;
import org.junit.BeforeClass;
import org.junit.Test;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 *
 * @author yanpengfang
 * @create 2019-01-31 9:56 PM
 */
public class DbTest {

    private static Badger badger;

    @BeforeClass
    public static void constructBadger() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/store");
        config.setUsername("yanpengfang");
        config.setPassword("272777475");
        config.setMaximumPoolSize(10);

        HikariDataSource dataSource = new HikariDataSource(config);

        badger = Badger.newInstance(dataSource);
    }

    @Test
    public void insertTest() throws Exception {
        Date now = new Date();
        Driver driver = new Driver();
        driver.setAge(43);
        driver.setDriverName("王叼蛋");
        driver.setType(TypeEnum.JOIN);
        driver.setCreateDate(now);
        driver.setUpdateDate(now);
        //保存数据并且回填自增主键id
        badger.save(driver);
        System.out.println("司机ID:" + driver.getDriverId());

        Order order = new Order();

        order.setOrderNo("P22437896" + System.currentTimeMillis());
        System.out.println("订单号:" + order.getOrderNo());
        order.setDriverId(driver.getDriverId());
        order.setMoney(new BigDecimal("189.02"));
        order.setUpdateDate(now);
        order.setCreateDate(now);
        badger.saveIgnore(order);
    }

    /**
     *  根据id删除
     * @throws Exception
     */
    @Test
    public void deleteTest() throws Exception {
        badger.delete(Order.class, "P22437896");
    }

    /**
     * 根据id更新
     * @throws Exception
     */
    @Test
    public void updateTest() throws Exception {
        Driver driver = badger.get(Driver.class, 1);
        if (driver == null || driver.getDriverId() == 0) {
            return;
        }
        driver.setAge(53);
        driver.setType(TypeEnum.SELF);
        badger.update(driver);
    }

    @Test
    public void deleteByConditionTest() throws Exception {
        DeleteStatement statement = badger.createDeteleStatement(Driver.class, "type=? and age=?");
        statement.addParam(TypeEnum.JOIN);
        statement.addParam(43);
        statement.execute();
    }

    @Test
    public void updateByConditionTest() throws Exception {
        UpdateStatement statement = badger.createUpdateStatement(Order.class,
                "money=?, update_date=?", "order_no=? and driver_id=?");
        statement.addParam(new BigDecimal("126"));
        statement.addParam(new Date());
        statement.addParam("P22437896");
        statement.addParam(2);//根据driver_id分表必须带分表字段
        statement.execute();
    }

    @Test
    public void selectByConditionTest() throws Exception {
        //根据条件查询所有字段
        Query query = badger.createQuery(Driver.class, "driver_id >=1 and driver_id <= ?");
        query.addParam(10);
        List<Driver> drivers = query.list();
        System.out.println(drivers);

        //根据条件查询指定字段
        Query queryOrder = badger.createQuery(Order.class, "order_no,money", "driver_id = ?");
        queryOrder.addParam(1); //根据driver_id分表必须带分表字段
        List<Order> orders = queryOrder.list();
        System.out.println(orders);
    }

    @Test
    public void selectByPageTest() throws Exception {
        Query query = badger.createQuery(Driver.class, "create_date>=? and create<=?");
        Date now = new Date();
        Date before = new Date(System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(20));
        query.addParam(before);
        query.addParam(now);

        //一共多少条
        long count = query.count();
        System.out.println("总条数:" + count);

        query.setPageIndex(0);
        query.setPageSize(10);

        List<Driver> drivers = query.list();
        System.out.println(drivers);
    }
}
