package com.googlecode.caparf.framework.base;

/**
 * Base class for cutting-and-packing algorithms.
 *
 * @param <I> algorithm input class
 * @param <O> algorithm output class
 *
 * @author denis.nsc@gmail.com (Denis Nazarov)
 */
public abstract class Algorithm<I extends BaseInput<? extends BaseItem>, O extends BaseOutput> {

  /**
   * Runs algorithm for the given {@code input} and returns {@code output}.
   *
   * @param input algorithm input
   * @return algorithm output
   */
  public abstract O solve(I input);

  /**
   * Returns display user-visible name which is equal to simple name of algorithm
   * class by default.
   *
   * @return user-understandable label
   */
  public String getDisplayName() {
    return this.getClass().getSimpleName();
  }

  @Override
  public String toString() {
    return getDisplayName();
  }
}
