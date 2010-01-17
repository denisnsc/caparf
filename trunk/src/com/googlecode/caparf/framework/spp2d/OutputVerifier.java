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

import com.googlecode.caparf.framework.base.BaseOutputVerifier;
import com.googlecode.caparf.framework.base.Verdict;
import com.googlecode.caparf.framework.base.Verdict.Result;

/**
 * Verifier of output for 2 Dimensional Strip Packing Problem.
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


    // Verifies that input and output has the same number of rectangle items
    if (input.getItemsCount() != output.getPlacementsCount()) {
      verdict.setResult(Result.INVALID_OUTPUT);
      verdict.setComment("Input and output has different number of rectangle items");
      return verdict;
    }

    // Verifies that each rectangle item fits into the strip
    for (int i = 0; i < input.getItemsCount(); i++) {
      Rectangle rect = input.getItems().get(i);
      RectanglePlacement placement = output.getPlacements().get(i);
      if (placement.getX() < 0 || placement.getX() + rect.getWidth() > input.getStripWidth() ||
          placement.getY() < 0) {
        verdict.setResult(Result.INVALID_OUTPUT);
        verdict.setComment("Rectangle item #" + i + " does not fit into the strip");
        return verdict;
      }
    }

    // Verifies that rectangle items do not intersect
    for (int i = 0; i < input.getItemsCount(); i++) {
      int xli = output.getPlacements().get(i).getX();
      int xri = xli + input.getItems().get(i).getWidth();
      int yli = output.getPlacements().get(i).getY();
      int yri = yli + input.getItems().get(i).getHeight();
      for (int j = i + 1; j < input.getItems().size(); j++) {
        int xlj = output.getPlacements().get(j).getX();
        int xrj = xlj + input.getItems().get(j).getWidth();
        int ylj = output.getPlacements().get(j).getY();
        int yrj = ylj + input.getItems().get(j).getHeight();

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

    // Output is correct
    verdict.setResult(Result.VALID_OUTPUT);

    return verdict;
  }
}
