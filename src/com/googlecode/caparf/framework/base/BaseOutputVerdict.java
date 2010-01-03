package com.googlecode.caparf.framework.base;

/**
 * Base class for output verification verdict.
 *
 * @author denis.nsc@gmail.com (Denis Nazarov)
 */
public class BaseOutputVerdict {

  /** Possible verdicts for output verification. */
  public enum Verdict {
    VALID_OUTPUT,
    INVALID_OUTPUT,
    TIME_LIMIT_EXCEEDED,
    MEMORY_LIMIT_EXCEEDED,
    RUNTIME_ERROR
  }

  /** Output verification verdict. */
  private Verdict verdict;

  /** Output verification comment. */
  private String comment;

  /**
   * @return output verification verdict
   */
  public Verdict getVerdict() {
    return verdict;
  }

  /**
   * Sets output verification verdict.
   *
   * @param verdict output verification verdict
   */
  public void setVerdict(Verdict verdict) {
    this.verdict = verdict;
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
    return getVerdict() + ": " + getComment();
  }
}
