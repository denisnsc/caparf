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

import java.util.List;

import com.googlecode.caparf.framework.base.BaseOutputVerifier;
import com.googlecode.caparf.framework.base.Verdict;
import com.googlecode.caparf.framework.base.Verdict.Result;
import com.googlecode.caparf.framework.items.Rectangle;
import com.googlecode.caparf.framework.items.RectanglePlacement;

/**
 * Verifier of output for 2 Dimensional Orthogonal Packing Problem.
 *
 * @param <O> algorithm output
 *
 * @author denis.nsc@gmail.com (Denis Nazarov)
 */
public class OutputVerifier implements BaseOutputVerifier<Input, Output> {

  @Override
  public Verdict verify(Input input, Output output) {
    Verdict verdict = new Verdict();

    // Verifies that output is not null
    if (output == null) {
      verdict.setResult(Result.INVALID_OUTPUT);
      verdict.setComment("Output is null");
      return verdict;
    }

    if (output.hasSolution()) {
      // Verifies that input and output has the same number of rectangle items
      int itemsCount = input.getItemsCount();
      if (itemsCount != output.getPlacementsCount()) {
        verdict.setResult(Result.INVALID_OUTPUT);
        verdict.setComment("Input and output has different number of rectangle items");
        return verdict;
      }


      // Verifies that each rectangle item fits into the bin
      List<Rectangle> rectangles = input.getItems();
      List<RectanglePlacement> placements = output.getPlacements();
      for (int i = 0; i < itemsCount; i++) {
        Rectangle rect = rectangles.get(i);
        RectanglePlacement placement = placements.get(i);
        if (placement.getX() < 0 || placement.getX() + rect.getWidth() > input.getBinWidth() ||
            placement.getY() < 0 || placement.getY() + rect.getHeight() > input.getBinHeight()) {
          verdict.setResult(Result.INVALID_OUTPUT);
          verdict.setComment("Rectangle item #" + i + " does not fit into the bin");
          return verdict;
        }
      }

      // Verifies that rectangle items do not intersect
      for (int i = 0; i < itemsCount; i++) {
        int xli = placements.get(i).getX();
        int xri = xli + rectangles.get(i).getWidth();
        int yli = placements.get(i).getY();
        int yri = yli + rectangles.get(i).getHeight();
        for (int j = i + 1; j < itemsCount; j++) {
          int xlj = placements.get(j).getX();
          int xrj = xlj + rectangles.get(j).getWidth();
          int ylj = placements.get(j).getY();
          int yrj = ylj + rectangles.get(j).getHeight();

          int xl = Math.max(xli, xlj);
          int xr = Math.max(Math.min(xri, xrj), xl);
          int yl = Math.max(yli, ylj);
          int yr = Math.max(Math.min(yri, yrj), yl);

          if ((xr - xl) * (yr - yl) > 0) {
            verdict.setResult(Result.INVALID_OUTPUT);
            verdict.setComment("Rectangle items #" + i + " and #" + j + " intersects");
            return verdict;
          }
        }
      }
    }

    // Output is correct
    verdict.setResult(Result.VALID_OUTPUT);

    return verdict;
  }
}
