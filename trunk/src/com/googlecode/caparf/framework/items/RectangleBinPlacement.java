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

package com.googlecode.caparf.framework.items;

import com.googlecode.caparf.framework.base.BaseItemPlacement;

/**
 * Rectangle item bin-placement which is defined by its lower-left point and bin
 * number (<code>0</code>-based).
 *
 * @author denis.nsc@gmail.com (Denis Nazarov)
 */
public class RectangleBinPlacement extends BaseItemPlacement {
  /** {@code x}-coordinate of rectangle lower-left point. */
  private int x;
  /** {@code y}-coordinate of rectangle lower-left point. */
  private int y;
  /** Number of bin into which rectangle is packed. */
  private int binNumber;

  /**
   * Constructs {@code RectangleBinPlacement} by the given coordinates of its
   * lower-left point and bin number.
   *
   * @param x {@code x}-coordinate of rectangle lower-left point
   * @param y {@code y}-coordinate of rectangle lower-left point
   * @param binNumber number of bin into which rectangle is packed
   */
  public RectangleBinPlacement(int x, int y, int binNumber) {
    this.x = x;
    this.y = y;
    this.binNumber = binNumber;
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

  /**
   * @return number of bin into which rectangle is packed
   */
  public int getBinNumber() {
    return binNumber;
  }
}
