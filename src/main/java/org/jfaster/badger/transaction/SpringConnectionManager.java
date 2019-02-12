package org.jfaster.badger.transaction;

import java.sql.Connection;

import javax.sql.DataSource;

import org.jfaster.badger.spi.SpiMeta;
import org.springframework.jdbc.datasource.DataSourceUtils;

@SpiMeta(name = "spring")
public class SpringConnectionManager implements ConnectionManager {

    public Connection getConnection(DataSource dataSource) {
        return DataSourceUtils.getConnection(dataSource);
    }


    public void releaseConnection(Connection conn, DataSource dataSource) {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }

}
