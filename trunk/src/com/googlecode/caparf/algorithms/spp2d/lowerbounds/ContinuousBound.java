package com.googlecode.caparf.algorithms.spp2d.lowerbounds;

import com.googlecode.caparf.framework.base.LowerBound;
import com.googlecode.caparf.framework.spp2d.Input;
import com.googlecode.caparf.framework.spp2d.Rectangle;

/**
 * Naive lower bound is simply the total area of rectangle items divided by
 * strip width. Time complexity is {@code O(n)}.
 *
 * @author denis.nsc@gmail.com (Denis Nazarov)
 */
public class ContinuousBound implements LowerBound<Input> {

  @Override
  public int calculateLowerBound(Input input) {
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
