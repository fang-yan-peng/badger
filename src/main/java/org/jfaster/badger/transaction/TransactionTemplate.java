package org.jfaster.badger.transaction;


import org.jfaster.badger.Badger;
import org.jfaster.badger.transaction.exception.TransactionException;
import org.jfaster.badger.transaction.exception.TransactionSystemException;

/**
 */
public class TransactionTemplate {

    public static void execute(
            Badger badger, String dataSourceFactoryName, TransactionIsolationLevel level,
            TransactionAction action) throws TransactionException {
        execute(TransactionFactory.newTransaction(badger, dataSourceFactoryName, level), action);
    }

    public static void execute(Badger badger, String dataSourceFactoryName, TransactionAction action)
            throws TransactionException {
        execute(TransactionFactory.newTransaction(badger, dataSourceFactoryName), action);
    }

    public static void execute(String dataSourceFactoryName, TransactionIsolationLevel level, TransactionAction action)
            throws TransactionException {
        execute(TransactionFactory.newTransaction(dataSourceFactoryName, level), action);
    }

    public static void execute(String dataSourceFactoryName, TransactionAction action) throws TransactionException {
        execute(TransactionFactory.newTransaction(dataSourceFactoryName), action);
    }

    public static void execute(TransactionIsolationLevel level, TransactionAction action) throws TransactionException {
        execute(TransactionFactory.newTransaction(level), action);
    }

    public static void execute(TransactionAction action) throws TransactionException {
        execute(TransactionFactory.newTransaction(), action);
    }

    private static void execute(Transaction transaction, TransactionAction action) throws TransactionException {
        TransactionStatus status = new TransactionStatus();
        try {
            action.doInTransaction(status);
        } catch (RuntimeException e) {
            transaction.rollback();
            throw e;
        } catch (Exception e) {
            transaction.rollback();
            throw new TransactionSystemException(e);
        }
        if (status.isRollbackOnly()) {
            transaction.setRollbackOnly(true);
        }
        transaction.commit();
    }

}
