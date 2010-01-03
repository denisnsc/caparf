package com.googlecode.caparf.framework.base;

/**
 * Base interface for cutting-and-packing algorithm's output. Concrete output
 * class must implement {@link #calculateObjectiveFunction()} that will return
 * the value of objective function to <b>minimize</b>. If the objective for some
 * problem is to maximize {@code f(...)} then one may return {@code -f(...)} as
 * value of objective function to minimize.
 * <p>
 * Note, that objective function is calculated every call.
 *
 * @author denis.nsc@gmail.com (Denis Nazarov)
 */
public interface BaseOutput {

  /**
   * Calculates value of objective function to minimize.
   *
   * @return objective function value
   */
  Number calculateObjectiveFunction();
}
