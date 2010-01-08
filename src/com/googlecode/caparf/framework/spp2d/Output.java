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

  /** Corresponding input. */
  private final Input input;

  /** Rectangle items' placement. */
  private final List<Point2D> placement;

  /**
   * Constructs output for 2 Dimensional Strip Packing Problem. Placement is the
   * ordered list of bottom-left points of corresponding rectangle items.
   *
   * @param input corresponding input for 2 Dimensional Strip Packing Problem
   * @param placement rectangle items' placement
   */
  public Output(Input input, List<Point2D> placement) {
    this.input = input;
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

  @Override
  public Number calculateObjectiveFunction() {
    int stripHeight = 0;
    for (int i = 0; i < placement.size(); i++) {
      stripHeight = Math.max(stripHeight, placement.get(i).y + input.getItems().get(i).getHeight());
    }
    return stripHeight;
  }
}
