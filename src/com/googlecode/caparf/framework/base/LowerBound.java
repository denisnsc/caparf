package com.googlecode.caparf.framework.base;

/**
 * Base interface for lower bounds.
 *
 * @param <I> algorithm input class
 *
 * @author denis.nsc@gmail.com (Denis Nazarov)
 */
public interface LowerBound<I extends BaseInput<? extends BaseItem>> {

  /**
   * Calculates the lower bound for the given {@code input}.
   *
   * @param input algorithm input
   * @return lower bound for the given {@code input}
   */
  Number calculateLowerBound(I input);
}
