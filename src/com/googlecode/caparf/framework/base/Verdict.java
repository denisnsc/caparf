package com.googlecode.caparf.framework.base;

import com.googlecode.caparf.framework.runner.RunInformation;

/**
 * Output verification verdict.
 *
 * @author denis.nsc@gmail.com (Denis Nazarov)
 */
public class Verdict {

  /** Possible results for output verification. */
  public enum Result {
    /** Output produced by algorithm is valid. */
    VALID_OUTPUT,
    /** Output produced by algorithm is invalid. */
    INVALID_OUTPUT,
    /** Algorithm failed to produce output. */
    FAILED_TO_RUN
  }

  /** Output verification result. */
  private Result result;

  /** Output verification comment. */
  private String comment;

  /** Information about algorithm execution. */
  private RunInformation runInformation;

  /**
   * @return output verification result
   */
  public Result getResult() {
    return result;
  }

  /**
   * Sets output verification result.
   *
   * @param result output verification result
   */
  public void setResult(Result result) {
    this.result = result;
  }

  /**
   * @return output verification comment
   */
  public String getComment() {
    return comment;
  }

  /**
   * Sets output verification comment.
   *
   * @param comment output verification comment
   */
  public void setComment(String comment) {
    this.comment = comment;
  }

  /**
   * @return information about algorithm execution
   */
  public RunInformation getRunInformation() {
    return runInformation;
  }

  /**
   * Sets information about algorithm execution.
   *
   * @param runInformation information about algorithm execution
   */
  public void setRunInformation(RunInformation runInformation) {
    this.runInformation = runInformation;
  }

  @Override
  public String toString() {
    return getResult() + ": " + getComment();
  }
}
