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

import java.util.ArrayList;
import java.util.List;

import com.googlecode.caparf.framework.base.BaseOutput;
import com.googlecode.caparf.framework.items.RectanglePlacement;

/**
 * Output for 2 Dimensional Orthogonal Packing Problem.
 *
 * @author denis.nsc@gmail.com (Denis Nazarov)
 */
public class Output extends BaseOutput<RectanglePlacement> {

  /** Indicates whether the needed placement was found or not. */
  private final boolean hasSolution;

  /**
   * Constructs output for 2 Dimensional Orthogonal Packing Problem. Placements
   * is the ordered list of bottom-left points of corresponding rectangle items.
   *
   * @param placements rectangle item placements
   */
  public Output(List<RectanglePlacement> placements) {
    super(placements);
    this.hasSolution = true;
  }

  /**
   * Constructs output for 2 Dimensional Orthogonal Packing Problem. Placements
   * is the ordered list of bottom-left points of corresponding rectangle items.
   *
   * @param placements rectangle item placements
   */
  public Output(RectanglePlacement[] placements) {
    super(placements);
    this.hasSolution = true;
  }

  /**
   * Constructs output for 2 Dimensional Orthogonal Packing Problem that has no
   * placements, i.e. the corresponding input is infeasible.
   */
  public Output() {
    super(new ArrayList<RectanglePlacement>());
    this.hasSolution = true;
  }

  /**
   * @return whether the needed placement was found or not
   */
  public boolean hasSolution() {
    return hasSolution;
  }

  @Override
  public Number calculateObjectiveFunction() {
    return hasSolution ? 1 : 0;
  }

  @Override
  public void transform(List<Integer> transformation) {
    if (hasSolution) {
      super.transform(transformation);
    }
  }

  @Override
  public void transform(int[] transformation) {
    if (hasSolution) {
      super.transform(transformation);
    }
  }
}
