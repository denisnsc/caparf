package com.googlecode.caparf.framework.spp2d;

import java.util.List;

import com.googlecode.caparf.framework.base.BaseInput;

/**
 * Input for 2 Dimensional Strip Packing Problem.
 *
 * @author denis.nsc@gmail.com (Denis Nazarov)
 */
public class Input extends BaseInput<Rectangle> {

  /** Width of strip. */
  private final int stripWidth;

  /**
   * Constructs input for 2 Dimensional Strip Packing Problem.
   *
   * @param rectangles rectangle items to be packed
   * @param stripWidth width of strip
   * @param identifier input identifier
   */
  public Input(List<Rectangle> rectangles, int stripWidth, String identifier) {
    super(rectangles, identifier);
    this.stripWidth = stripWidth;
  }

  /**
   * Constructs input for 2 Dimensional Strip Packing Problem.
   *
   * @param rectangles rectangle items to be packed
   * @param stripWidth width of strip
   * @param identifier input identifier
   */
  public Input(Rectangle[] rectangles, int stripWidth, String identifier) {
    super(rectangles, identifier);
    this.stripWidth = stripWidth;
  }

  /**
   * Returns width of strip into which rectangle items to be packed.
   *
   * @return width of strip
   */
  public int getStripWidth() {
    return stripWidth;
  }
}
