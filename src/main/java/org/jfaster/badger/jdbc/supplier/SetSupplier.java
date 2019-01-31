package org.jfaster.badger.jdbc.supplier;

import java.util.Set;

import org.jfaster.badger.spi.Spi;

@Spi
public interface SetSupplier {

   <T> Set<T> get(Class<T> clazz);

}
