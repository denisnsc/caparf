package com.googlecode.caparf.framework.spp2d;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.googlecode.caparf.framework.base.BaseInput;

/**
 * Input for 2 Dimensional Strip Packing Problem.
 *
 * @author denis.nsc@gmail.com (Denis Nazarov)
 */
public class Input extends BaseInput {

  /** Rectangle item. */
  public static class Rectangle {
    public int width;
    public int height;

    public Rectangle(int width, int height) {
      this.width = width;
      this.height = height;
    }
  }

  /** List of rectangle items. */
  private final List<Rectangle> rectangles;

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
    super(identifier);
    this.rectangles = rectangles;
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
    super(identifier);
    this.rectangles = new ArrayList<Rectangle>(rectangles.length);
    Collections.addAll(this.rectangles, rectangles);
    this.stripWidth = stripWidth;
  }

  /**
   * Constructs input for 2 Dimensional Strip Packing Problem.
   *
   * @param widths     widths of rectangle items to be packed
   * @param heights    heights of rectangle items to be packed
   * @param stripWidth width of strip
   * @param identifier input identifier
   */
  public Input(int[] widths, int[] heights, int stripWidth, String identifier) {
    super(identifier);
    rectangles = new ArrayList<Rectangle>(widths.length);
    for (int i = 0; i < widths.length; i++) {
      rectangles.add(new Rectangle(widths[i], heights[i]));
    }
    this.stripWidth = stripWidth;
  }

  /**
   * Returns all rectangle items to be packed. Order of items matters.
   *
   * @return rectangle items to be packed
   */
  public List<Rectangle> getRectangles() {
    return rectangles;
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
