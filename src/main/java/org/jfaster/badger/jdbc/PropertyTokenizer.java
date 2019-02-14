package org.jfaster.badger.jdbc;

import org.jfaster.badger.util.Strings;

import lombok.Getter;

/**
 * @author Clinton Begin
 */
public class PropertyTokenizer {

  @Getter
  private String name;

  @Getter
  private String children;

  public PropertyTokenizer(String fullname) {
    if (fullname != null) {
      int delim = fullname.indexOf('.');
      if (delim > -1) {
        name = fullname.substring(0, delim);
        children = fullname.substring(delim + 1);
      } else {
        name = Strings.emptyToNull(fullname);
        children = null;
      }
    }
  }

  public boolean hasCurrent() {
    return name != null;
  }

  public boolean hasNext() {
    return children != null;
  }

  public PropertyTokenizer next() {
    return new PropertyTokenizer(children);
  }

}
