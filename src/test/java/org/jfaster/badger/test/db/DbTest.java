package org.jfaster.badger.test.db;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.jfaster.badger.Badger;
import org.jfaster.badger.exception.MappingException;
import org.jfaster.badger.sql.delete.DeleteStatement;
import org.jfaster.badger.sql.select.Query;
import org.jfaster.badger.sql.select.SQLQuery;
import org.jfaster.badger.sql.update.UpdateSqlStatement;
import org.jfaster.badger.sql.update.UpdateStatement;
import org.jfaster.badger.test.entity.Driver;
import org.jfaster.badger.test.entity.DriverExt;
import org.jfaster.badger.test.entity.DriverOrder;
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

    /**
     * 构建Badger实例
     */
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

    /**
     * 插入，插入所有字段，插入非空字段，插入唯一索引冲突忽略。
     * @throws Exception
     */
    @Test
    public void insertTest() throws Exception {
        Date now = new Date();
        Driver driver = new Driver();
        driver.setAge(43);
        driver.setDriverName("王叼蛋");
        driver.setType(TypeEnum.JOIN);
        driver.setCreateDate(now);

        //保存非空字段并且回填自增主键id
        badger.saveNotNull(driver);

        driver = new Driver();
        driver.setAge(43);
        driver.setDriverName("刘叼蛋");
        driver.setType(TypeEnum.SELF);
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
        //忽略唯一索引冲突
        badger.saveIgnore(order);
    }

    /**
     *  根据id删除。
     * @throws Exception
     */
    @Test(expected = MappingException.class)
    public void deleteTest() throws Exception {
        badger.delete(Driver.class, 3);
        //分库分表字段不是id则抛异常
        badger.delete(Order.class, "P224378961549863778117");
    }

    /**
     * 根据id更新所有字段。
     * @throws Exception
     */
    @Test
    public void updateTest() throws Exception {
        Driver driver = badger.get(Driver.class, 14);
        if (driver == null || driver.getDriverId() == 0) {
            return;
        }
        driver.setAge(53);
        driver.setType(TypeEnum.SELF);
        badger.update(driver);
    }

    /**
     * 根据条件删除
     * @throws Exception
     */
    @Test
    public void deleteByConditionTest() throws Exception {
        DeleteStatement statement = badger.createDeteleStatement(Driver.class, "type=? and age=?");
        statement.addParam(TypeEnum.JOIN);
        statement.addParam(43);
        statement.execute();
    }

    /**
     * 根据条件更新指定字段。
     * @throws Exception
     */
    @Test
    public void updateByConditionTest() throws Exception {
        UpdateStatement statement = badger.createUpdateStatement(Order.class,
                "money=?, update_date=?", "order_no=? and driver_id=?");
        statement.addParam(new BigDecimal("126"));
        statement.addParam(new Date());
        statement.addParam("P224378961549867525895");
        statement.addParam(13);//根据driver_id分表必须带分表字段
        statement.execute();
    }

    /**
     * 根据条件查询，查询指定字段。
     * @throws Exception
     */
    @Test
    public void selectByConditionTest() throws Exception {
        //根据条件查询所有字段
        Query<Driver> query = badger.createQuery(Driver.class, "driver_id >=1 and driver_id <= ?");
        query.addParam(14);
        List<Driver> drivers = query.list();
        System.out.println(drivers);

        //根据条件查询指定字段
        Query<Order> queryOrder = badger.createQuery(Order.class, "order_no,money", "driver_id = ?");
        queryOrder.addParam(13); //根据driver_id分表必须带分表字段
        List<Order> orders = queryOrder.list();
        System.out.println(orders);
    }

    /**
     * 分页查询
     * @throws Exception
     */
    @Test
    public void selectByPageTest() throws Exception {
        Query<Driver> query = badger.createQuery(Driver.class, "create_date >= ? and create_date <= ?");
        Date now = new Date();
        Date before = new Date(System.currentTimeMillis() - TimeUnit.HOURS.toMillis(10));
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

    /**
     * 扩展表结构
     * @throws Exception
     */
    @Test
    public void selectExt() throws Exception {
        Query<DriverExt> query = badger.createQuery(DriverExt.class, "avg(age) as avgAge, driver_id", "1=1 group by driver_id");
        List<DriverExt> driverExts = query.list();
        System.out.println(driverExts);
    }

    /**
     * 自定义sql更新，不支持分库分表，分库分表信息得自己实现
     */
    @Test
    public void updateBySelfDefine() throws Exception {
        UpdateSqlStatement sqlStatement = badger.createUpdateSqlStatement("update driver set update_date=? where driver_id=?");
        sqlStatement.addParam(new Date());
        sqlStatement.addParam(14);
        sqlStatement.execute();
    }

    /**
     * 自定义sql查询，不支持分库分表，分库分表信息得自己实现，复杂sql查询可以拆分成多次单表查询。大表本身不建议join
     */
    @Test
    public void selectBySelfDefine() throws Exception {
        SQLQuery<DriverOrder> query = badger.createSqlQuery(DriverOrder.class, "select a.driver_id,b.order_no from driver a join driver_order_1 b on a.driver_id=b.driver_id where a.driver_id=?");
        query.addParam(13);
        List<DriverOrder> driverOrders = query.list();
        System.out.println(driverOrders);
    }
}
