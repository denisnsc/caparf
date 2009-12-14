package com.googlecode.caparf.framework.spp2d;

/**
 * Base interface for 2 Dimensional Strip Packing Problem lower bounds.
 *
 * @author denis.nsc@gmail.com (Denis Nazarov)
 */
public interface LowerBound {

  /**
   * Calculates the lower bound for the given {@code input}.
   *
   * @param input 2 Dimensional Strip Packing Problem input
   * @return lower bound for the given {@code input}
   */
  int calculateLowerBound(Input input);
}
