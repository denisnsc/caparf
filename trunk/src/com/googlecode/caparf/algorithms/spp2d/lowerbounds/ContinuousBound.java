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

package com.googlecode.caparf.algorithms.spp2d.lowerbounds;

import com.googlecode.caparf.framework.base.LowerBound;
import com.googlecode.caparf.framework.items.Rectangle;
import com.googlecode.caparf.framework.spp2d.Input;

/**
 * Naive lower bound is simply the total area of rectangle items divided by
 * strip width. Time complexity is {@code O(n)}.
 *
 * @author denis.nsc@gmail.com (Denis Nazarov)
 */
public class ContinuousBound implements LowerBound<Input> {

  @Override
  public Number calculateLowerBound(Input input) {
    int itemsArea = 0;
    for (Rectangle rect : input.getItems()) {
      itemsArea += rect.getHeight() * rect.getWidth();
    }
    int result = itemsArea / input.getStripWidth();
    if (itemsArea % input.getStripWidth() > 0) {
      result += 1;
    }
    return result;
  }
}
