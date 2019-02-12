package org.jfaster.badger.transaction;

@FunctionalInterface
public interface TransactionAction {

  void doInTransaction(TransactionStatus status) throws Exception;

}
