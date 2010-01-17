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