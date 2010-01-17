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