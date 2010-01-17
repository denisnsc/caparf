package com.googlecode.caparf.framework.base;

/**
 * Base interface for CAPARF cloneable classes.
 *
 * @author denis.nsc@gmail.com (Denis Nazarov)
 */
public interface BaseCloneable extends Cloneable {

  /** {@inheritDoc} */
  public Object clone();
}
