package org.jfaster.badger.transaction;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.jfaster.badger.spi.SpiMeta;
import org.jfaster.badger.transaction.exception.CannotGetJdbcConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpiMeta(name = "badger")
public class BadgerConnectionManager implements ConnectionManager {

    private final static Logger logger = LoggerFactory.getLogger(BadgerConnectionManager.class);

    public Connection getConnection(DataSource dataSource) throws CannotGetJdbcConnectionException {
        try {
            return doGetConnection(dataSource);
        } catch (SQLException e) {
            throw new CannotGetJdbcConnectionException("Could not get JDBC Connection", e);
        }
    }

    private Connection doGetConnection(DataSource dataSource) throws SQLException {
        ConnectionHolder connHolder = TransactionSynchronizationManager.getConnectionHolder(dataSource);
        if (connHolder != null) {
            if (logger.isDebugEnabled()) {
                logger.debug("Fetching resumed JDBC Connection from DataSource");
            }
            return connHolder.getConnection();
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Fetching JDBC Connection from DataSource");
        }
        Connection conn = dataSource.getConnection();
        if (DataSourceMonitor.needCheckAutoCommit(dataSource)) { // 如果使用事务后，归还conn时，重置autoCommit失败，则需要检测
            try {
                if (!conn.getAutoCommit()) {
                    conn.setAutoCommit(true);
                }
            } catch (Throwable e) {
                logger.error("Could not set autoCommit of JDBC Connection after get Connection, so close it", e);
                releaseConnection(conn, dataSource);
                throw new CannotGetJdbcConnectionException("Could not set autoCommit of JDBC Connection " +
                        "after get Connection, so close it", e);
            }
        }
        return conn;
    }


    public void releaseConnection(Connection conn, DataSource dataSource) {
        try {
            doReleaseConnection(conn, dataSource);
        } catch (SQLException e) {
            logger.error("Could not close JDBC Connection", e);
        } catch (Throwable e) {
            logger.error("Unexpected exception on closing JDBC Connection", e);
        }
    }

    private void doReleaseConnection(Connection conn, DataSource dataSource) throws SQLException {
        if (conn == null) {
            return;
        }
        ConnectionHolder connHolder = TransactionSynchronizationManager.getConnectionHolder(dataSource);
        if (connHolder != null && connectionEquals(connHolder, conn)) {
            return;
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Returning JDBC Connection to DataSource");
        }
        conn.close();
    }

    private boolean connectionEquals(ConnectionHolder connHolder, Connection passedInConn) {
        Connection heldConn = connHolder.getConnection();
        return heldConn == passedInConn || heldConn.equals(passedInConn);
    }

}
