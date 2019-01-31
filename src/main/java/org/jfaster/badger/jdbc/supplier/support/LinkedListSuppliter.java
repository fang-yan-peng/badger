package org.jfaster.badger.jdbc.supplier.support;

import java.util.LinkedList;
import java.util.List;

import org.jfaster.badger.jdbc.supplier.ListSupplier;
import org.jfaster.badger.spi.SpiMeta;

@SpiMeta(name = "linkedList")
public class LinkedListSuppliter implements ListSupplier {

  @Override
  public <T> List<T> get(Class<T> clazz) {
    return new LinkedList<>();
  }

}
