package org.jfaster.badger.query.bean;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Set;

import lombok.Getter;

public class PropertyMeta {

  @Getter
  private final String name;

  @Getter
  private final Class<?> type;

  @Getter
  private final Method readMethod;

  @Getter
  private final Method writeMethod;

  private final Set<Annotation> readMethodAnnos;

  private final Set<Annotation> writeMethodAnnos;

  private final Set<Annotation> propertyAnnos;

  public PropertyMeta(
      String name, Class<?> type, Method readMethod, Method writeMethod,
          Set<Annotation> readMethodAnnos,
          Set<Annotation> writeMethodAnnos,
          Set<Annotation> propertyAnnos) {
    this.name = name;
    this.type = type;
    this.readMethod = readMethod;
    this.writeMethod = writeMethod;
    this.readMethodAnnos = readMethodAnnos;
    this.writeMethodAnnos = writeMethodAnnos;
    this.propertyAnnos = propertyAnnos;
  }

  public <T extends Annotation> T getReadMethodAnno(Class<T> annotationType) {
    for (Annotation anno : readMethodAnnos) {
      if (annotationType.isInstance(anno)) {
        return annotationType.cast(anno);
      }
    }
    return null;
  }

  public <T extends Annotation> T getWriteMethodAnno(Class<T> annotationType) {
    for (Annotation anno : writeMethodAnnos) {
      if (annotationType.isInstance(anno)) {
        return annotationType.cast(anno);
      }
    }
    return null;
  }

  public <T extends Annotation> T getPropertyAnno(Class<T> annotationType) {
    for (Annotation anno : propertyAnnos) {
      if (annotationType.isInstance(anno)) {
        return annotationType.cast(anno);
      }
    }
    return null;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof PropertyMeta)) {
      return false;
    }
    PropertyMeta other = (PropertyMeta) obj;
    return Objects.equals(this.name, other.name) &&
            Objects.equals(this.type, other.type) &&
            Objects.equals(this.readMethod, other.readMethod) &&
            Objects.equals(this.writeMethod, other.writeMethod) &&
            Objects.equals(this.readMethodAnnos, other.readMethodAnnos) &&
            Objects.equals(this.writeMethodAnnos, other.writeMethodAnnos) &&
            Objects.equals(this.propertyAnnos, other.propertyAnnos);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, type, readMethod, writeMethod, readMethodAnnos, writeMethodAnnos,
            propertyAnnos);
  }
}
