package org.jfaster.badger.jdbc.supplier.support;

import java.util.ArrayList;
import java.util.List;

import org.jfaster.badger.jdbc.supplier.ListSupplier;
import org.jfaster.badger.spi.SpiMeta;

@SpiMeta(name = "arrayList")
public class ArrayListSuppliter implements ListSupplier {

  @Override
  public <T> List<T> get(Class<T> clazz) {
    return new ArrayList<>();
  }

}
