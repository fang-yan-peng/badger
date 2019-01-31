package org.jfaster.badger.jdbc.supplier;

import java.util.List;

import org.jfaster.badger.spi.Spi;

@Spi
public interface ListSupplier {

   <T> List<T> get(Class<T> clazz);

}
