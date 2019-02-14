package org.jfaster.badger.transaction;

import java.sql.Connection;

import javax.sql.DataSource;

import org.jfaster.badger.spi.Spi;

/**
 * 连接管理器
 * @author yanpengfang
 * create 2019-02-12 9:42 PM
 */
@Spi
public interface ConnectionManager {

    Connection getConnection(DataSource dataSource);

    void releaseConnection(Connection conn, DataSource dataSource);

}
