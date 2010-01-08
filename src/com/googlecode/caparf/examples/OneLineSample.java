package com.googlecode.caparf.examples;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.caparf.framework.base.Algorithm;
import com.googlecode.caparf.framework.spp2d.Input;
import com.googlecode.caparf.framework.spp2d.Output;
import com.googlecode.caparf.framework.spp2d.Rectangle;

public class OneLineSample extends Algorithm<Input, Output> {
  @Override
  public Output solve(Input input) {
    List<Output.Point2D> placement = new ArrayList<Output.Point2D>(input.getItemsCount());
    int currentHeight = 0;
    for (Rectangle rect : input.getItems()) {
      // Place current rectangle on the top of the previous rectangle
      Output.Point2D lowerLeftPoint = new Output.Point2D();
      lowerLeftPoint.x = 0;
      lowerLeftPoint.y = currentHeight;
      placement.add(lowerLeftPoint);
      // Add height of the rectangle to the current height
      currentHeight += rect.getHeight();
    }
    return new Output(input, placement);
  }
}