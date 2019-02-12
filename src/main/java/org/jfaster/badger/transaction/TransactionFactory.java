package org.jfaster.badger.transaction;

import java.sql.Connection;
import java.util.List;

import javax.sql.DataSource;

import org.jfaster.badger.Badger;
import org.jfaster.badger.jdbc.datasource.support.AbstractDataSourceFactory;
import org.jfaster.badger.transaction.exception.CannotCreateTransactionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author ash
 */
public abstract class TransactionFactory {

    private final static Logger logger = LoggerFactory.getLogger(TransactionFactory.class);

    public static Transaction newTransaction(Badger badger, String dataSourceFactoryName, TransactionIsolationLevel level) {
        DataSource dataSource = badger.getMasterDataSource(dataSourceFactoryName);
        if (dataSource == null) {
            throw new IllegalArgumentException("Can't find master DataSource from mango [" + badger + "] " +
                    "with datasource factory name [" + dataSourceFactoryName + "]");
        }
        return newTransaction(dataSource, level);
    }

    public static Transaction newTransaction(Badger badger, String dataSourceFactoryName) {
        return newTransaction(badger, dataSourceFactoryName, TransactionIsolationLevel.DEFAULT);
    }

    public static Transaction newTransaction(String dataSourceFactoryName, TransactionIsolationLevel level) {
        List<Badger> badgers = Badger.getInstances();
        if (badgers.size() != 1) {
            throw new IllegalStateException("The number of instances badger expected 1 but " + badgers.size() + ", " +
                    "Please specify badger instance");
        }
        return newTransaction(badgers.get(0), dataSourceFactoryName, level);
    }

    public static Transaction newTransaction(String dataSourceFactoryName) {
        return newTransaction(dataSourceFactoryName, TransactionIsolationLevel.DEFAULT);
    }

    public static Transaction newTransaction(TransactionIsolationLevel level) {
        return newTransaction(AbstractDataSourceFactory.DEFULT_NAME, level);
    }

    public static Transaction newTransaction() {
        return newTransaction(AbstractDataSourceFactory.DEFULT_NAME, TransactionIsolationLevel.DEFAULT);
    }

    public static Transaction newTransaction(DataSource dataSource) {
        return newTransaction(dataSource, TransactionIsolationLevel.DEFAULT);
    }

    private static Transaction newTransaction(DataSource dataSource, TransactionIsolationLevel level) {
        if (dataSource == null) {
            throw new IllegalArgumentException("DataSource can't be null");
        }
        if (level == null) {
            throw new IllegalArgumentException("TransactionIsolationLevel can't be null");
        }
        ConnectionHolder connHolder = TransactionSynchronizationManager.getConnectionHolder(dataSource);
        return connHolder != null ?
                usingExistingTransaction(dataSource) :
                createNewTransaction(dataSource, level);
    }

    private static Transaction usingExistingTransaction(DataSource dataSource) {
        if (logger.isDebugEnabled()) {
            logger.debug("Using existing transaction");
        }
        Transaction transaction = new TransactionImpl(false, dataSource);
        return transaction;
    }

    private static Transaction createNewTransaction(DataSource dataSource, TransactionIsolationLevel expectedLevel) {
        if (logger.isDebugEnabled()) {
            logger.debug("Creating new transaction");
        }
        Connection conn = null;
        try {
            Integer previousLevel = null;
            boolean isMustRestoreAutoCommit = false;
            conn = dataSource.getConnection();
            if (logger.isDebugEnabled()) {
                logger.debug("Acquired Connection [" + conn + "] for JDBC transaction");
            }

            // 设置事务的隔离级别
            if (expectedLevel != TransactionIsolationLevel.DEFAULT) {
                previousLevel = conn.getTransactionIsolation();
                if (previousLevel != expectedLevel.getLevel()) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Changing isolation level of JDBC Connection [" + conn + "] to " +
                                expectedLevel.getLevel());
                    }
                    conn.setTransactionIsolation(expectedLevel.getLevel());
                }
            }

            // 设置自动提交为false
            if (conn.getAutoCommit()) {
                isMustRestoreAutoCommit = true;
                if (logger.isDebugEnabled()) {
                    logger.debug("Switching JDBC Connection [" + conn + "] to manual commit");
                }
                conn.setAutoCommit(false);
            }

            Transaction transaction = new TransactionImpl(true, dataSource, previousLevel, isMustRestoreAutoCommit);
            ConnectionHolder connHolder = new ConnectionHolder(conn);
            TransactionSynchronizationManager.bindConnectionHolder(dataSource, connHolder);
            return transaction;
        } catch (Throwable e) {
            DataSourceUtils.releaseConnection(conn, dataSource);
            throw new CannotCreateTransactionException("Could not open JDBC Connection for transaction", e);
        }
    }

}
