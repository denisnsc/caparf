package com.googlecode.caparf.framework;

public abstract class AlgorithmOutputVerdict {
  public enum Verdict {
    CORRECT_ANSWER,
    WRONG_ANSWER,
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
