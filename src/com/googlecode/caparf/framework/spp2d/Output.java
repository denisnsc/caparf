package com.googlecode.caparf.framework.spp2d;

import java.util.List;

import com.googlecode.caparf.framework.base.BaseOutput;

/**
 * Output for 2 Dimensional Strip Packing Problem.
 * 
 * @author denis.nsc@gmail.com (Denis Nazarov)
 */
public class Output implements BaseOutput {

  /** Point in 2 Dimensional space. */
  public static class Point2D {
    public int x;
    public int y;
  }

  /** Rectangle items' placement. */
  private final List<Point2D> placement;

  /**
   * Constructs output for 2 Dimensional Strip Packing Problem. Placement is the
   * ordered list of bottom-left points of corresponding rectangle items.
   * 
   * @param placement rectangle items' placement
   */
  public Output(List<Point2D> placement) {
    this.placement = placement;
  }

  /**
   * Sets rectangle items' placement.
   * 
   * @param placement
   */
  public List<Point2D> getPlacement() {
    return placement;
  }
  
  @Override
  public String toString() {
    String ret = "";
    for (Point2D point : placement) {
      if (ret.length() > 0) {
        ret += ", ";
      }
      ret += "(" + point.x + ", " + point.y + ")";
    }
    return ret;
  }
}
