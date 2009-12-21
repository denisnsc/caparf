package com.googlecode.caparf.framework.base;

/**
 * Base class for output verdict.
 *
 * @author denis.nsc@gmail.com (Denis Nazarov)
 */
public abstract class BaseOutputVerdict {

  /** Possible verdicts for output verification. */
  public enum Verdict {
    VALID_OUTPUT,
    INVALID_OUTPUT,
    TIME_LIMIT_EXCEEDED,
    MEMORY_LIMIT_EXCEEDED,
    RUNTIME_ERROR
  }

  private Verdict verdict;
  private String comment;

  public Verdict getVerdict() {
    return verdict;
  }
  public void setVerdict(Verdict verdict) {
    this.verdict = verdict;
  }
  public String getComment() {
    return comment;
  }
  public void setComment(String comment) {
    this.comment = comment;
  }
}
