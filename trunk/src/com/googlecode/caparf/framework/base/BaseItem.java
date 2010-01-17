package com.googlecode.caparf.framework.base;

/**
 * Base class for cutting-and-packing algorithm input's item.
 *
 * @author denis.nsc@gmail.com (Denis Nazarov)
 */
public class BaseItem implements BaseCloneable {
  
  @Override
  public Object clone() {
    try {
      return super.clone();
    } catch (CloneNotSupportedException e) {
      // this shouldn't happen, since we are Cloneable
      throw new InternalError();
    }
  }
}
