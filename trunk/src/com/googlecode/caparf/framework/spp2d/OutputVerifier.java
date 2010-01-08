package com.googlecode.caparf.framework.spp2d;

import com.googlecode.caparf.framework.base.BaseOutputVerifier;
import com.googlecode.caparf.framework.base.Verdict;
import com.googlecode.caparf.framework.base.Verdict.Result;
import com.googlecode.caparf.framework.spp2d.Output.Point2D;

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
    if (input.getItemsCount() != output.getPlacement().size()) {
      verdict.setResult(Result.INVALID_OUTPUT);
      verdict.setComment("Input and output has different number of rectangle items");
      return verdict;
    }

    // Verifies that each rectangle item fits into the strip
    for (int i = 0; i < input.getItemsCount(); i++) {
      Rectangle rect = input.getItems().get(i);
      Point2D position = output.getPlacement().get(i);
      if (position.x < 0 || position.x + rect.getWidth() > input.getStripWidth() ||
          position.y < 0) {
        verdict.setResult(Result.INVALID_OUTPUT);
        verdict.setComment("Rectangle item #" + i + " does not fit into the strip");
        return verdict;
      }
    }

    // Verifies that rectangle items do not intersect
    for (int i = 0; i < input.getItemsCount(); i++) {
      int xli = output.getPlacement().get(i).x;
      int xri = xli + input.getItems().get(i).getWidth();
      int yli = output.getPlacement().get(i).y;
      int yri = yli + input.getItems().get(i).getHeight();
      for (int j = i + 1; j < input.getItems().size(); j++) {
        int xlj = output.getPlacement().get(j).x;
        int xrj = xlj + input.getItems().get(j).getWidth();
        int ylj = output.getPlacement().get(j).y;
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
