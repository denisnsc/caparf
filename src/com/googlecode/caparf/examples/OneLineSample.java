package com.googlecode.caparf.examples;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.caparf.framework.base.Algorithm;
import com.googlecode.caparf.framework.spp2d.Input;
import com.googlecode.caparf.framework.spp2d.Output;
import com.googlecode.caparf.framework.spp2d.Rectangle;
import com.googlecode.caparf.framework.spp2d.RectanglePlacement;

public class OneLineSample extends Algorithm<Input, Output> {
  @Override
  public Output solve(Input input) {
    List<RectanglePlacement> placement = new ArrayList<RectanglePlacement>(input.getItemsCount());
    int currentHeight = 0;
    for (Rectangle rect : input.getItems()) {
      // Place current rectangle on the top of the previous rectangle
      placement.add(new RectanglePlacement(0, currentHeight));
      // Add height of the rectangle to the current height
      currentHeight += rect.getHeight();
    }
    return new Output(input, placement);
  }
}