package com.googlecode.caparf.framework.base;

/**
 * Base class for cutting-and-packing algorithm output's item placement.
 *
 * @author denis.nsc@gmail.com (Denis Nazarov)
 */
public class BaseItemPlacement implements BaseCloneable {
  
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
