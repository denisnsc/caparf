package com.googlecode.caparf.framework.spp2d;

import com.googlecode.caparf.framework.AlgorithmOutputVerifier;
import com.googlecode.caparf.framework.spp2d.Input.Rectangle;
import com.googlecode.caparf.framework.spp2d.Output.Point2D;

/**
 * Verifier of output for 2 Dimensional Strip Packing Problem.
 *
 * @param <O> algorithm output
 *
 * @author denis.nsc@gmail.com (Denis Nazarov)
 */
public class OutputVerifier<I extends Input, O extends Output> implements
    AlgorithmOutputVerifier<I, O> {

  @Override
  public boolean verify(I input, O output) {
    // Verifies that input and output has the same number of rectangle items
    if (input.getRectangles().size() != output.getPlacement().size()) {
      return false;
    }

    // Verifies that each rectangle item fits into the strip
    for (int i = 0; i < input.getRectangles().size(); i++) {
      Rectangle rect = input.getRectangles().get(i);
      Point2D position = output.getPlacement().get(i);
      if (position.x < 0 || position.x + rect.width > input.getStripWidth() || position.y < 0) {
        return false;
      }
    }

    // Verifies that rectangle items do not intersect
    for (int i = 0; i < input.getRectangles().size(); i++) {
      int xli = output.getPlacement().get(i).x;
      int xri = xli + input.getRectangles().get(i).width;
      int yli = output.getPlacement().get(i).x;
      int yri = yli + input.getRectangles().get(i).height;
      for (int j = i + 1; j < input.getRectangles().size(); j++) {
        int xlj = output.getPlacement().get(j).x;
        int xrj = xlj + input.getRectangles().get(j).width;
        int ylj = output.getPlacement().get(j).x;
        int yrj = ylj + input.getRectangles().get(j).height;

        int xl = Math.max(xli, xlj);
        int xr = Math.max(Math.min(xri, xrj), xl);
        int yl = Math.max(yli, ylj);
        int yr = Math.max(Math.min(yri, yrj), yl);

        if ((xr - xl) * (yr - yl) > 0) {
          return false;
        }
      }
    }

    return true;
  }
}
