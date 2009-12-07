package com.googlecode.caparf.framework.spp2d;

import com.googlecode.caparf.framework.AlgorithmInput;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Input for 2 Dimensional Strip Packing Problem.
 *
 * @author denis.nsc@gmail.com (Denis Nazarov)
 */
public class Input implements AlgorithmInput {

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

  /** Brief description of input. */
  private String description;

  /**
   * Constructs input for 2 Dimensional Strip Packing Problem.
   *
   * @param rectangles rectangle items to be packed
   * @param stripWidth width of strip
   */
  public Input(List<Rectangle> rectangles, int stripWidth) {
    this.rectangles = rectangles;
    this.stripWidth = stripWidth;
  }

  /**
   * Constructs input for 2 Dimensional Strip Packing Problem.
   *
   * @param rectangles rectangle items to be packed
   * @param stripWidth width of strip
   */
  public Input(Rectangle[] rectangles, int stripWidth) {
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
   */
  public Input(int[] widths, int[] heights, int stripWidth) {
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

  /** Returns brief description of the input. */
  public String getDescription() {
    return description;
  }

  /** Sets brief description of the input. */
  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return getDescription() + "(n = " + rectangles.size() + ", stripWidth = " + stripWidth + ")";
  }
}
