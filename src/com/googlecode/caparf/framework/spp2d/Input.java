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
