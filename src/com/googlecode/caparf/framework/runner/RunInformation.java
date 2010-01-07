package com.googlecode.caparf.framework.runner;

/**
 * General information about algorithm or lower bound execution including
 * <ol>
 * <li>Whether the execution was successful or not
 * <li>Exception thrown by algorithm (or lower bound) if any
 * <li>Time elapsed during algorithm (or lower bound) execution
 * </ol>
 *
 * @author denis.nsc@gmail.com (Denis Nazarov)
 */
public class RunInformation {

  /** Possible run results. */
  public static enum RunResult {
    /** Everything was ok. */
    OK,
    /** Execution took more than time limit. */
    TIME_LIMIT_EXCEDED,
    /** Exception was thrown during execution. */
    EXCEPTION
  }

  private RunResult runResult;
  private long timeElapsed;
  private Exception exception;

  /**
   * Constructs RunInformation which is initialized with {@link RunResult#OK}.
   */
  public RunInformation() {
    runResult = RunResult.OK;
    timeElapsed = 0;
    exception = null;
  }

  /**
   * @return run result
   */
  public RunResult getResult() {
    return runResult;
  }

  /**
   * Sets run result.
   *
   * @param runResult run result
   */
  public void setResult(RunResult runResult) {
    this.runResult = runResult;
  }

  /**
   * @return time elapsed during execution in milliseconds
   */
  public long getTimeElapsed() {
    return timeElapsed;
  }

  /**
   * Sets time elapsed during execution in milliseconds.
   *
   * @param millis elapsed time in milliseconds
   */
  public void setTimeElapsed(long millis) {
    this.timeElapsed = millis;
  }

  /**
   * @return exception thrown during execution or null
   */
  public Exception getException() {
    return exception;
  }

  /**
   * Sets exception thrown during execution.
   *
   * @param exception exception thrown during execution
   */
  public void setException(Exception exception) {
    this.exception = exception;
  }
}
