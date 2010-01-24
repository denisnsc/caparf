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

import com.googlecode.caparf.framework.base.BaseOutput;

/**
 * Output for 2 Dimensional Strip Packing Problem.
 *
 * @author denis.nsc@gmail.com (Denis Nazarov)
 */
public class Output extends BaseOutput<RectanglePlacement> {

  /** Corresponding input. */
  private Input input;

  /**
   * Constructs output for 2 Dimensional Strip Packing Problem. Placements is the
   * ordered list of bottom-left points of corresponding rectangle items.
   *
   * @param input corresponding input for 2 Dimensional Strip Packing Problem
   * @param placements rectangle item placements
   */
  public Output(Input input, List<RectanglePlacement> placements) {
    super(placements);
    this.input = input;
  }

  @Override
  public Number calculateObjectiveFunction() {
    int stripHeight = 0;
    List<Rectangle> rectangles = input.getItems();
    for (int i = 0; i < getPlacementsCount(); i++) {
      stripHeight = Math.max(stripHeight, placements.get(i).getY() +
          rectangles.get(i).getHeight());
    }
    return stripHeight;
  }

  @Override
  public void transform(List<Integer> transformation) {
    super.transform(transformation);
    input.transform(transformation);
  }

  @Override
  public String toString() {
    String ret = "";
    for (RectanglePlacement placement : placements) {
      if (ret.length() > 0) {
        ret += ", ";
      }
      ret += "(" + placement.getX() + ", " + placement.getY() + ")";
    }
    return ret;
  }
}
