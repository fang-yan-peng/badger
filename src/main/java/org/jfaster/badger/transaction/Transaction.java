package org.jfaster.badger.transaction;

/**
 */
public interface Transaction {

    void commit();

    void rollback();

    boolean isRollbackOnly();

    void setRollbackOnly(boolean rollbackOnly);

}
