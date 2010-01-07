package com.googlecode.caparf.framework.base;

import com.googlecode.caparf.framework.runner.Runner;

/**
 * Interface that should be implemented by algorithms or lower bounds that can
 * interrupt their computations. If interruptible algorithm (or lower bound) is
 * run with time limit using {@link Runner} then {@link #interrupt()} will be
 * called after algorithm (or lower bound) exceeds the time limit. After that
 * {@link Runner#EXTRA_TIME_LIMIT} milliseconds will be given to algorithm (or
 * lower bound) to finish computations and return result.
 *
 * @author denis.nsc@gmail.com (Denis Nazarov)
 * @see Runner
 */
public interface Interruptible {

  /**
   * Interrupts current computations and asks to return result as soon as
   * possible.
   */
  public void interrupt();
}
