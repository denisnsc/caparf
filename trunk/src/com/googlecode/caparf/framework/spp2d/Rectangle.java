package com.googlecode.caparf.framework.spp2d;

import com.googlecode.caparf.framework.base.BaseItem;

/**
 * Rectangle item which is defined by its width and height.
 *
 * @author denis.nsc@gmail.com (Denis Nazarov)
 */
public class Rectangle extends BaseItem {
  /** Rectangle width. */
  private int width;
  /** Rectangle height. */
  private int height;

  /**
   * Constructs {@code Rectangle} by the given {@code width} and {@code height}.
   *
   * @param width rectangle width
   * @param height rectangle height
   */
  public Rectangle(int width, int height) {
    this.width = width;
    this.height = height;
  }

  /**
   * @return rectangle width
   */
  public int getWidth() {
    return width;
  }

  /**
   * @return rectangle height
   */
  public int getHeight() {
    return height;
  }
}