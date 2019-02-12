package org.jfaster.badger.transaction;

/**
 */
public class TransactionStatus {

  private boolean rollbackOnly = false;

  public boolean isRollbackOnly() {
    return rollbackOnly;
  }

  public void setRollbackOnly(boolean rollbackOnly) {
    this.rollbackOnly = rollbackOnly;
  }

}
