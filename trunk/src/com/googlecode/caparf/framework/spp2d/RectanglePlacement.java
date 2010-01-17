package com.googlecode.caparf.framework.spp2d;

import com.googlecode.caparf.framework.base.BaseItemPlacement;

/**
 * Rectangle item placement which is defined by its lower-left point.
 *
 * @author denis.nsc@gmail.com (Denis Nazarov)
 */
public class RectanglePlacement extends BaseItemPlacement {
  /** {@code x}-coordinate of rectangle lower-left point. */
  private int x;
  /** {@code y}-coordinate of rectangle lower-left point. */
  private int y;

  /**
   * Constructs {@code RectanglePlacement} by the given coordinates of its lower-left point.
   *
   * @param x {@code x}-coordinate of rectangle lower-left point
   * @param y {@code y}-coordinate of rectangle lower-left point
   */
  public RectanglePlacement(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * @return {@code x}-coordinate of rectangle lower-left point
   */
  public int getX() {
    return x;
  }

  /**
   * @return {@code y}-coordinate of rectangle lower-left point
   */
  public int getY() {
    return y;
  }
}
