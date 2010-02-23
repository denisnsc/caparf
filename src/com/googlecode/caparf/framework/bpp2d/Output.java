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

package com.googlecode.caparf.framework.bpp2d;

import java.util.List;

import com.googlecode.caparf.framework.base.BaseOutput;
import com.googlecode.caparf.framework.items.RectangleBinPlacement;

/**
 * Output for 2 Dimensional Bin Packing Problem.
 *
 * @author denis.nsc@gmail.com (Denis Nazarov)
 */
public class Output extends BaseOutput<RectangleBinPlacement> {

  /**
   * Constructs output for 2 Dimensional Bin Packing Problem. Placements is the
   * ordered list of bottom-left points and bin number of corresponding
   * rectangle items.
   *
   * @param placements rectangle item placements
   */
  public Output(List<RectangleBinPlacement> placements) {
    super(placements);
  }

  /**
   * Constructs output for 2 Dimensional Bin Packing Problem. Placements is the
   * ordered list of bottom-left points and bin number of corresponding
   * rectangle items.
   *
   * @param placements rectangle item bin-placements
   */
  public Output(RectangleBinPlacement[] placements) {
    super(placements);
  }

  @Override
  public Number calculateObjectiveFunction() {
    int maximalBinNumber = 0;
    for (RectangleBinPlacement placement : getPlacements()) {
      maximalBinNumber = Math.max(maximalBinNumber, placement.getBinNumber());
    }
    return maximalBinNumber + 1;
  }

  @Override
  public void transform(List<Integer> transformation) {
    super.transform(transformation);
  }

  @Override
  public void transform(int[] transformation) {
    super.transform(transformation);
  }
}
