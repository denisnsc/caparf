/*
 * Copyright (C) 2010 Denis Nazarov <denis.nsc@gmail.com>.
 *
 * This file is part of caparf (http://code.google.com/p/caparf/).
 *
 * caparf is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * caparf is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with caparf. If not, see <http://www.gnu.org/licenses/>.
 */

package com.googlecode.caparf.framework.opp2d;

import java.util.List;

import com.googlecode.caparf.framework.base.BaseInput;
import com.googlecode.caparf.framework.items.Rectangle;

/**
 * Input for 2 Dimensional Orthogonal Packing Problem.
 *
 * @author denis.nsc@gmail.com (Denis Nazarov)
 */
public class Input extends BaseInput<Rectangle> {

  /** Width of bin. */
  private final int binWidth;

  /** Height of bin. */
  private final int binHeight;

  /**
   * Constructs input for 2 Dimensional Orthogonal Packing Problem.
   *
   * @param rectangles rectangle items to be packed
   * @param binWidth width of bin
   * @param binHeight height of bin
   * @param identifier input identifier
   */
  public Input(List<Rectangle> rectangles, int binWidth, int binHeight, String identifier) {
    super(rectangles, identifier);
    this.binWidth = binWidth;
    this.binHeight = binHeight;
  }

  /**
   * Constructs input for 2 Dimensional Orthogonal Packing Problem.
   *
   * @param rectangles rectangle items to be packed
   * @param binWidth width of bin
   * @param binHeight height of bin
   * @param identifier input identifier
   */
  public Input(Rectangle[] rectangles, int binWidth, int binHeight, String identifier) {
    super(rectangles, identifier);
    this.binWidth = binWidth;
    this.binHeight = binHeight;
  }

  /**
   * Returns width of bin into which rectangle items to be packed.
   *
   * @return width of bin
   */
  public int getBinWidth() {
    return binWidth;
  }

  /**
   * Returns height of bin into which rectangle items to be packed.
   *
   * @return height of bin
   */
  public int getBinHeight() {
    return binHeight;
  }
}
