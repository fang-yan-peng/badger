package org.jfaster.badger.transaction;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;


import org.jfaster.badger.spi.ExtensionLoader;
import org.jfaster.badger.transaction.exception.IllegalTransactionStateException;
import org.jfaster.badger.transaction.exception.TransactionSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TransactionImpl implements Transaction {

  private final static Logger logger = LoggerFactory.getLogger(TransactionImpl.class);

  private final boolean newTransaction;

  private final DataSource dataSource;

  private final Integer previousLevel;

  private final boolean mustRestoreAutoCommit;

  private boolean completed = false;

  private boolean rollbackOnly = false;

  public TransactionImpl(boolean newTransaction, DataSource dataSource) {
    this(newTransaction, dataSource, null, true);
  }

  public TransactionImpl(boolean newTransaction, DataSource dataSource,
                         Integer previousLevel, boolean mustRestoreAutoCommit) {
    this.newTransaction = newTransaction;
    this.dataSource = dataSource;
    this.previousLevel = previousLevel;
    this.mustRestoreAutoCommit = mustRestoreAutoCommit;
  }

  @Override
  public void commit() {
    if (completed) {
      throw new IllegalTransactionStateException(
          "Transaction is already completed - do not call commit or rollback more than once per transaction");
    }

    ConnectionHolder connHolder = TransactionSynchronizationManager.getConnectionHolder(dataSource);
    if (connHolder == null) {
      throw new IllegalStateException("No ConnectionHolder bind to DataSource [" + dataSource + "]");
    }

    if (!newTransaction) { // 嵌套的事务，不真正提交
      if (logger.isDebugEnabled()) {
        logger.debug("Commit transaction is not new");
      }
      if (rollbackOnly) {
        if (logger.isDebugEnabled()) {
          logger.debug("Marking transaction as rollback-only");
        }
        connHolder.setRollbackOnly(true); // 促发顶层事务回滚
      }
      return;
    }

    if (rollbackOnly || connHolder.isRollbackOnly()) { // 嵌套的事务出现回滚则回滚
      if (logger.isDebugEnabled()) {
        logger.debug("Transaction is marked as rollback-only, so will rollback");
      }
      processRollback(connHolder.getConnection());
      return;
    }

    // 提交事务
    processCommit(connHolder.getConnection());
  }

  @Override
  public void rollback() {
    if (completed) {
      throw new IllegalTransactionStateException(
          "Transaction is already completed - do not call commit or rollback more than once per transaction");
    }

    ConnectionHolder connHolder = TransactionSynchronizationManager.getConnectionHolder(dataSource);
    if (connHolder == null) {
      throw new IllegalStateException("No ConnectionHolder bind to DataSource [" + dataSource + "]");
    }

    if (!newTransaction) {
      if (logger.isDebugEnabled()) {
        logger.debug("Rollback transaction is not new, marking transaction as rollback-only");
      }
      connHolder.setRollbackOnly(true); // 促发顶层事务回滚
      return;
    }

    processRollback(connHolder.getConnection());
  }

  @Override
  public boolean isRollbackOnly() {
    return rollbackOnly;
  }

  @Override
  public void setRollbackOnly(boolean rollbackOnly) {
    this.rollbackOnly = rollbackOnly;
  }

  private void processCommit(Connection conn) {
    try {
      doCommit(conn);
    } catch (Exception e) {
      doRollbackOnCommitException(conn, e);
    } finally {
      cleanup(conn);
    }
  }

  private void doCommit(Connection conn) {
    try {
      if (logger.isDebugEnabled()) {
        logger.debug("Committing JDBC transaction on Connection [" + conn + "]");
      }
      conn.commit();
    } catch (SQLException e) {
      throw new TransactionSystemException("Could not commit JDBC transaction", e);
    }
  }

  private void doRollbackOnCommitException(Connection conn, Exception e) {
    try {
      doRollback(conn);
    } catch (TransactionSystemException tes) {
      logger.error("Commit exception overridden by rollback exception", e);
      throw tes;
    }
  }

  private void processRollback(Connection conn) {
    try {
      doRollback(conn);
    } finally {
      cleanup(conn);
    }
  }

  private void doRollback(Connection conn) {
    try {
      if (logger.isDebugEnabled()) {
        logger.debug("Rolling back JDBC transaction on Connection [" + conn + "]");
      }
      conn.rollback();
    } catch (SQLException e) {
      throw new TransactionSystemException("Could not roll back JDBC transaction", e);
    }
  }

  private void cleanup(Connection conn) {
    TransactionSynchronizationManager.unbindConnectionHolder(dataSource);
    resetConnectionAfterTransaction(conn);
    ConnectionManager manager = ExtensionLoader.get(ConnectionManager.class).getExtension("badger");
    manager.releaseConnection(conn, dataSource);
    completed = true;
  }

  private void resetConnectionAfterTransaction(Connection conn) {

    try {
      if (mustRestoreAutoCommit) {
        if (logger.isDebugEnabled()) {
          logger.debug("Switching JDBC Connection to auto commit");
        }
        conn.setAutoCommit(true);
      }
    } catch (SQLException e) {
      DataSourceMonitor.resetAutoCommitFail(dataSource);
      logger.error("Could not reset autoCommit of JDBC Connection after transaction", e);
    } catch (Throwable e) {
      DataSourceMonitor.resetAutoCommitFail(dataSource);
      logger.error("Unexpected exception on resetting autoCommit of JDBC Connection after transaction", e);
    }
    try {
      if (previousLevel != null) {
        if (logger.isDebugEnabled()) {
          logger.debug("Resetting isolation level of JDBC Connection to " + previousLevel);
        }
        conn.setTransactionIsolation(previousLevel);
      }
    } catch (SQLException e) {
      logger.error("Could not reset isolation level of JDBC Connection after transaction", e);
    } catch (Throwable e) {
      logger.error("Unexpected exception on resetting isolation level of JDBC Connection after transaction", e);
    }
  }
}
