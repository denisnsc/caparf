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

package com.googlecode.caparf.inputs.spp2d;

import java.util.ArrayList;
import java.util.List;

/**
 * Converts other problems inputs to 2 Dimensional Strip Packing Problem inputs.
 *
 * @author denis.nsc@gmail.com (Denis Nazarov)
 */
public class Converter {

  /**
   * Converts 2 Dimensional Orthogonal Packing Problem input to 2 Dimensional
   * Strip Packing Problem input.
   *
   * @param opp2dInput 2 Dimensional Orthogonal Packing Problem input
   * @return 2 Dimensional Strip Packing Problem input corresponding to the
   *         given 2 Dimensional Orthogonal Packing Problem input
   */
  public static com.googlecode.caparf.framework.spp2d.Input convert(
      com.googlecode.caparf.framework.opp2d.Input opp2dInput) {
    return new com.googlecode.caparf.framework.spp2d.Input(opp2dInput.getItems(),
        opp2dInput.getBinWidth(), opp2dInput.getIdentifier().replaceFirst("opp2d", "spp2d"));
  }

  /**
   * Converts 2 Dimensional Orthogonal Packing Problem inputs to 2 Dimensional
   * Strip Packing Problem inputs.
   *
   * @param opp2dInput list of 2 Dimensional Orthogonal Packing Problem inputs
   * @return list of 2 Dimensional Strip Packing Problem inputs corresponding to
   *         the given list of 2 Dimensional Orthogonal Packing Problem inputs
   */
  public static List<com.googlecode.caparf.framework.spp2d.Input> convert(
      List<com.googlecode.caparf.framework.opp2d.Input> opp2dInputs) {
    ArrayList<com.googlecode.caparf.framework.spp2d.Input> result =
        new ArrayList<com.googlecode.caparf.framework.spp2d.Input>(opp2dInputs.size());
    for (com.googlecode.caparf.framework.opp2d.Input input : opp2dInputs) {
      result.add(convert(input));
    }
    return result;
  }
}
