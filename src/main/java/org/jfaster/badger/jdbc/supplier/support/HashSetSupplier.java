package org.jfaster.badger.jdbc.supplier.support;

import java.util.HashSet;
import java.util.Set;

import org.jfaster.badger.jdbc.supplier.SetSupplier;
import org.jfaster.badger.spi.SpiMeta;

@SpiMeta(name = "hashSet")
public class HashSetSupplier implements SetSupplier {

  @Override
  public <T> Set<T> get(Class<T> clazz) {
    return new HashSet<>();
  }

}
