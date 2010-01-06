package com.googlecode.caparf.framework.base;

/**
 * Output verification verdict.
 *
 * @author denis.nsc@gmail.com (Denis Nazarov)
 */
public class Verdict {

  /** Possible results for output verification. */
  public enum Result {
    VALID_OUTPUT,
    INVALID_OUTPUT,
    TIME_LIMIT_EXCEEDED,
    RUNTIME_ERROR
  }

  /** Output verification result. */
  private Result result;

  /** Output verification comment. */
  private String comment;

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

  @Override
  public String toString() {
    return getResult() + ": " + getComment();
  }
}
