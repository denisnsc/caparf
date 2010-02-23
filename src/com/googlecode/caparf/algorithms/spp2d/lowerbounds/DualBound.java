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

import java.util.ArrayList;
import java.util.List;

import com.googlecode.caparf.framework.base.LowerBound;
import com.googlecode.caparf.framework.items.Rectangle;
import com.googlecode.caparf.framework.spp2d.Input;

/**
 * The idea of this lower bound is used by many authors. It starts with
 * calculating lower bound for original input. Lets the resulting bound be equal
 * {@code L}. Then a dual "input" is created by rotating rectangles with strip
 * width equal to {@code L}. If the lower bound for "dual" input is greater than
 * the strip width for the original input then the value of {@code L} is
 * increased until lower bound for "dual" input will be less or equal to the
 * strip width for the original input. Lets the final value of lower bound be
 * {@code L'}.
 * <p>
 * {@code DualBound} can be used only to improve existing lower bound. Time
 * complexity of this implementation is {@code O((L' - L) * f(N))}, where
 * {@code f(N)} is the time complexity of "nested "lower bound.
 *
 * @author denis.nsc@gmail.com (Denis Nazarov)
 */
public class DualBound implements LowerBound<Input> {

  /**
   * Nested lower bound that is used to calculate bounds for both original and
   * dual inputs.
   */
  private LowerBound<Input> nestedBound;

  /**
   * Constructs {@code DualBound} instance by the given {@code nestedBound}.
   *
   * @param nestedBound nested lower bound that will be used to calculate bounds
   *          for both original and dual inputs
   */
  public DualBound(LowerBound<Input> nestedBound) {
    this.nestedBound = nestedBound;
  }

  @Override
  public Number calculateLowerBound(Input input) {
    int originalBound = nestedBound.calculateLowerBound(input).intValue();
    int dualBound = originalBound;
    List<Rectangle> rotatedRectangles = new ArrayList<Rectangle>(input.getItemsCount());
    for (Rectangle rect : input.getItems()) {
      rotatedRectangles.add(new Rectangle(rect.getHeight(), rect.getWidth()));
    }
    while (true) {
      Input dualInput = new Input(rotatedRectangles, dualBound, input.getIdentifier());
      if (nestedBound.calculateLowerBound(dualInput).intValue() <= input.getStripWidth()) {
        break;
      }
      dualBound++;
    }
    return dualBound;
  }
}
