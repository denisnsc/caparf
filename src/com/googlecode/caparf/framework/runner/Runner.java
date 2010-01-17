package com.googlecode.caparf.framework.runner;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.Callable;

import com.googlecode.caparf.framework.base.Algorithm;
import com.googlecode.caparf.framework.base.BaseInput;
import com.googlecode.caparf.framework.base.BaseItem;
import com.googlecode.caparf.framework.base.BaseItemPlacement;
import com.googlecode.caparf.framework.base.BaseOutput;
import com.googlecode.caparf.framework.base.Interruptible;
import com.googlecode.caparf.framework.base.LowerBound;
import com.googlecode.caparf.framework.base.TimeLimitable;
import com.googlecode.caparf.framework.runner.RunInformation.RunResult;

/**
 * Runs algorithms and lower bounds with the given time limit (or without it)
 * safely (i.e. all exceptions will be caught) and returns result of run.
 * Additionally, information about occurred error (like exceeding time limit or
 * thrown exception) and time elapsed during run can be retrieved.
 *
 * @author denis.nsc@gmail.com (Denis Nazarov)
 */
public final class Runner {

  /** Extra time limit that will be given to a task to finish its work. */
  public static final long EXTRA_TIME_LIMIT = 100;

  /** Number of nanoseconds in one millisecond. */
  protected static final long NANOS_PER_MILLIS = 1000000;

  /** Maximal possible error in clock subtracted from 1. */
  protected static final double CLOCK_ERROR = 0.95;

  /**
   * Runs the given {@code algorithm} on the given {@code input} without time
   * limit.
   *
   * @param <I> algorithm input class
   * @param <O> algorithm output class
   * @param algorithm algorithm to run
   * @param input input that will be passed to algorithm
   * @return algorithm output or null in case of any problems
   */
  public static <I extends BaseInput<? extends BaseItem>,
      O extends BaseOutput<? extends BaseItemPlacement>> O run(final Algorithm<I, O> algorithm,
          final I input) {
    return run(algorithm, input, 0);
  }

  /**
   * Runs the given {@code algorithm} on the given {@code input} with the given
   * {@code timeLimit} in milliseconds. {@code timeLimit} is the maximal time
   * {@code algorithm} can spend on solving {@code input} until it will be
   * interrupted. {@code algorithm} will be terminated after
   * {@link #EXTRA_TIME_LIMIT} milliseconds after interruption if it didn't stop
   * running.
   *
   * @param <I> algorithm input class
   * @param <O> algorithm output class
   * @param algorithm algorithm to run
   * @param input input that will be passed to algorithm
   * @param timeLimit maximal time algorithm can run in milliseconds, {@code 0}
   *          for infinity
   * @return algorithm output or null in case of any problems
   */
  public static <I extends BaseInput<? extends BaseItem>,
      O extends BaseOutput<? extends BaseItemPlacement>> O run(final Algorithm<I, O> algorithm,
          final I input, long timeLimit) {
    return run(algorithm, input, timeLimit, null);
  }

  /**
   * Runs the given {@code algorithm} on the given {@code input} with the given
   * {@code timeLimit} in milliseconds. {@code timeLimit} is the maximal time
   * {@code algorithm} can spend on solving {@code input} until it will be
   * interrupted. {@code algorithm} will be terminated after
   * {@link #EXTRA_TIME_LIMIT} milliseconds after interruption if it didn't stop
   * running. Information about algorithm run will be stored in the given
   * {@code runInfo}.
   *
   * @param <I> algorithm input class
   * @param <O> algorithm output class
   * @param algorithm algorithm to run
   * @param input input that will be passed to algorithm
   * @param timeLimit maximal time algorithm can run in milliseconds, {@code 0}
   *          for infinity
   * @param runInfo information about algorithm run
   * @return algorithm output or null in case of any problems
   */
  public static <I extends BaseInput<? extends BaseItem>,
      O extends BaseOutput<? extends BaseItemPlacement>> O run(final Algorithm<I, O> algorithm,
          final I input, long timeLimit, RunInformation runInfo) {
    Callable<O> task = new Callable<O>() {
      @Override
      public O call() throws Exception {
        return algorithm.solve(input);
      }
    };
    return run(task, algorithm, timeLimit, runInfo);
  }

  /**
   * Runs the given {@code lowerBound} on the given {@code input} without time
   * limit.
   *
   * @param <I> algorithm input class
   * @param lowerBound lower bound to run
   * @param input input that will be passed to lower bound
   * @return lower bound value or null in case of any problems
   */
  public static <I extends BaseInput<? extends BaseItem>> Number run(final LowerBound<I> lowerBound,
      final I input) {
    return run(lowerBound, input, 0);
  }

  /**
   * Runs the given {@code lowerBound} on the given {@code input} with the given
   * {@code timeLimit} in milliseconds. {@code timeLimit} is the maximal time
   * {@code lowerBound} can spend on solving {@code input} until it will be
   * interrupted. {@code lowerBound} will be terminated after
   * {@link #EXTRA_TIME_LIMIT} milliseconds after interruption if it didn't stop
   * running.
   *
   * @param <I> algorithm input class
   * @param lowerBound lower bound to run
   * @param input input that will be passed to lower bound
   * @param timeLimit maximal time lower bound can run in milliseconds, {@code
   *          0} for infinity
   * @return lower bound value or null in case of any problems
   */
  public static <I extends BaseInput<? extends BaseItem>> Number run(final LowerBound<I> lowerBound,
      final I input, long timeLimit) {
    return run(lowerBound, input, timeLimit, null);
  }

  /**
   * Runs the given {@code lowerBound} on the given {@code input} with the given
   * {@code timeLimit} in milliseconds. {@code timeLimit} is the maximal time
   * {@code lowerBound} can spend on solving {@code input} until it will be
   * interrupted. {@code lowerBound} will be terminated after
   * {@link #EXTRA_TIME_LIMIT} milliseconds after interruption if it didn't stop
   * running. Information about lower bound run will be stored in the given
   * {@code runInfo}.
   *
   * @param <I> algorithm input class
   * @param lowerBound lower bound to run
   * @param input input that will be passed to lower bound
   * @param timeLimit maximal time lower bound can run in milliseconds, {@code
   *          0} for infinity
   * @param runInfo information about lower bound run
   * @return lower bound value or null in case of any problems
   */
  public static <I extends BaseInput<? extends BaseItem>> Number run(final LowerBound<I> lowerBound,
      final I input, long timeLimit, RunInformation runInfo) {
    Callable<Number> task = new Callable<Number>() {
      @Override
      public Number call() throws Exception {
        return lowerBound.calculateLowerBound(input);
      }
    };
    return run(task, lowerBound, timeLimit, runInfo);
  }

  /**
   * This function does the real work. It runs a {@code task} and collects its
   * result, exception (if any) and run statistics. Also it notifies {@code
   * listener} on run phases if it implements needed interfaces.
   */
  @SuppressWarnings("deprecation")  // for Thread.stop()
  private static <T> T run(Callable<T> task, Object listener, long timeLimit,
      RunInformation runInfo) {
    if (timeLimit < 0) {
      throw new IllegalArgumentException("time limit is negative");
    }

    // Task will be executed in a separate thread
    Wrapper<T> wrapper = new Wrapper<T>(task);
    Thread job = new Thread(wrapper);
    if (runInfo != null) {
      runInfo.setResult(RunResult.OK);
    }
    long jobId = job.getId();

    // Let listener know the time limit if it is interested in it
    if (listener instanceof TimeLimitable) {
      ((TimeLimitable) listener).setTimeLimit(timeLimit);
    }

    // ThreadMXBean interface to JVM is used to measure CPU time
    ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
    job.start();
    long limit = timeLimit;
    while (limit >= EXTRA_TIME_LIMIT && job.isAlive()) {
      try {
        // Job can consume a little bit more than limit during job.join(limit)
        limit = (long) (limit * CLOCK_ERROR);
        job.join(limit);
      } catch (InterruptedException e) {
      }
      limit = timeLimit - threadMXBean.getThreadCpuTime(jobId) / NANOS_PER_MILLIS;
    }

    // Notify (if possible) listener that time is about to exceed the limit and
    // give job extra time to finish its work
    if (job.isAlive()) {
      if (listener instanceof Interruptible) {
        ((Interruptible) listener).interrupt();
      }
      try {
        job.join(EXTRA_TIME_LIMIT);
      } catch (InterruptedException e) {
      }
    }

    // Stop thread if it is alive after time limit plus epsilon. Thread.stop()
    // is deprecated since it can be unsafe to stop thread this way. However, it
    // is the only possible way to terminate the thread in our case. Moreover,
    // it is more or less safe to stop thread with algorithm (or lower bound)
    // since it does not lock any resources needed for CAPARF.
    if (job.isAlive()) {
      if (runInfo != null) {
        runInfo.setResult(RunResult.TIME_LIMIT_EXCEDED);
      }
      job.stop();
    }

    if (runInfo != null) {
      runInfo.setTimeElapsed(wrapper.getTimeElapsed());
      Exception thrownException = wrapper.getException();
      if (thrownException != null) {
        runInfo.setException(thrownException);
        runInfo.setResult(RunResult.EXCEPTION);
      }
    }

    return wrapper.getResult();
  }

  /** Wraps callable task, executes it and collects all parameters. */
  private static class Wrapper<T> implements Runnable {
    private Callable<T> task;
    private T result;
    private Exception exception;
    private long timeElapsed;

    public Wrapper(Callable<T> task) {
      this.task = task;
      this.result = null;
      this.exception = null;
    }

    @Override
    public void run() {
      try {
        result = task.call();
      } catch (Exception e) {
        result = null;
        exception = e;
      }
      timeElapsed = ManagementFactory.getThreadMXBean().getCurrentThreadCpuTime() /
          NANOS_PER_MILLIS;
    }

    /**
     * @return task result, may be null
     */
    public T getResult() {
      return result;
    }

    /**
     * @return thrown exception, may be null
     */
    public Exception getException() {
      return exception;
    }

    /**
     * @return time elapsed for this thread
     */
    public long getTimeElapsed() {
      return timeElapsed;
    }
  }
}
