package com.googlecode.caparf.framework.spp2d;

/**
 * Output verdict for 2 Dimensional Strip Packing Problem.
 * 
 * @author denis.nsc@gmail.com (Denis Nazarov)
 */
public class OutputVerdict extends com.googlecode.caparf.framework.base.BaseOutputVerdict {
  private int stripHeight;

  public int getStripHeight() {
    return stripHeight;
  }

  public void setStripHeight(int stripHeight) {
    this.stripHeight = stripHeight;
  }
  
  @Override
  public String toString() {
    String comment = "";
    if (getVerdict() == Verdict.VALID_OUTPUT) {
      comment = "strip height = " + stripHeight;
    } else {
      comment = getComment();
    }
    return getVerdict() + ": " + comment;
  }
}
