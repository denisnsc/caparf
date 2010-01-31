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

package com.googlecode.caparf.inputs.opp2d;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.googlecode.caparf.framework.items.Rectangle;
import com.googlecode.caparf.framework.opp2d.Input;

/**
 * Test instances generator proposed by Clautiuax. For the reference look at <a
 * href="http://dx.doi.org/10.1016/j.ejor.2005.12.048">A new exact method for
 * the two-dimensional orthogonal packing problem (original article)</a> or at
 * <a href=
 * "http://www2.lifl.fr/~clautiau/pmwiki/pmwiki.php?n=Research.Benchmarks"
 * >François Clautiaux home page</a>.
 *
 * @author denis.nsc@gmail.com (Denis Nazarov)
 */
public class ClautiauxGenerator {

  /** Prefix of inputs identifiers produced by {@code ClautiauxGenerator}. */
  public static final String INPUT_IDENTIFIER_PREFIX = "opp2d.Clautiaux.";

  /**
   * Generates single input by the given parameters.
   *
   * @param itemsCount number of items to generate
   * @param discrepancy discrepancy between the total area of the items and the
   *          area of the bin, in percents
   * @return generated input
   */
  // TODO(dnazarov): implement generator once all details will be clear
  public Input generateInstance(int itemsCount, int discrepancy) {
    throw new UnsupportedOperationException();
  }

  /**
   * @return reference instances provided by François Clautiaux
   */
  public static List<Input> getReferenceInstances() {
    return REFERENCE_INSTANCES;
  }

  /** Reference instances. */
  private static final List<Input> REFERENCE_INSTANCES;

  /** Name of resource with reference instances. */
  private static final String REFERENCE_INSTANCES_RESOURCE_NAME = "ClautiauxReferenceInstances.txt";

  static {
    REFERENCE_INSTANCES = new ArrayList<Input>();
    Scanner scanner = new Scanner(ClautiauxGenerator.class
        .getResourceAsStream(REFERENCE_INSTANCES_RESOURCE_NAME));
    while (scanner.hasNext()) {
      String instanceId = scanner.next();
      int binWidth = scanner.nextInt();
      int binHeight = scanner.nextInt();
      int itemsCount = scanner.nextInt();
      Rectangle items[] = new Rectangle[itemsCount];
      for (int i = 0; i < itemsCount; i++) {
        items[i] = new Rectangle(scanner.nextInt(), scanner.nextInt());
      }
      REFERENCE_INSTANCES.add(new Input(items, binWidth, binHeight, INPUT_IDENTIFIER_PREFIX +
          instanceId));
    }
  }
}
