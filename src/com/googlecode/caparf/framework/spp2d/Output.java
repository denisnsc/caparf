package com.googlecode.caparf.framework.spp2d;

import java.util.List;

import com.googlecode.caparf.framework.AlgorithmOutput;

/**
 * Output for 2 Dimensional Strip Packing Problem.
 *
 * @author denis.nsc@gmail.com (Denis Nazarov)
 */
public class Output implements AlgorithmOutput {

  /** Point in 2 Dimensional space. */
  public static class Point2D {
    public int x;
    public int y;
  }

  /** Rectangle items' placement. */
  private List<Point2D> placement;

  /**
   * Constructs output for 2 Dimensional Strip Packing Problem.
   */
  public Output() {
    this.placement = null;
  }

  /**
   * Sets rectangle items' placement. Placement is the ordered list of
   * bottom-left points of corresponding rectangle items.
   *
   * @param placement rectangle items' placement
   */
  public void setPlacement(List<Point2D> placement) {
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
}
