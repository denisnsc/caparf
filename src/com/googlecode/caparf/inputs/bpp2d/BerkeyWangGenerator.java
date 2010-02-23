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

package com.googlecode.caparf.inputs.bpp2d;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import com.googlecode.caparf.framework.bpp2d.Input;
import com.googlecode.caparf.framework.items.Rectangle;

/**
 * Test instances generator proposed by Berkey and Wang. For the reference look
 * at <a href="http://www.jstor.org/pss/2582731">Two Dimensional Finite
 * Bin-Packing Algorithms (original article)</a> or at <a
 * href="http://www.or.deis.unibo.it/research_pages/ORinstances/lmv99.ps">
 * Heuristic and Metaheuristic Approaches for a Class of Two-Dimensional Bin
 * Packing Problems</a>.
 *
 * @author denis.nsc@gmail.com (Denis Nazarov)
 */
public class BerkeyWangGenerator {

  public static final String INPUT_IDENTIFIER_PREFIX = "bpp2d.Berkey and Wang.";

  public enum Type {
    CLASS_I   (1, 10,  1, 10,  10,  10),
    CLASS_II  (1, 10,  1, 10,  30,  30),
    CLASS_III (1, 35,  1, 35,  40,  40),
    CLASS_IV  (1, 35,  1, 35,  100, 100),
    CLASS_V   (1, 100, 1, 100, 100, 100),
    CLASS_VI  (1, 100, 1, 100, 300, 300);

    // Items' width is uniformly random in [minWidth .. maxWidth]
    int minWidth, maxWidth;
    // Items' height is uniformly random in [minHeight .. maxHeight]
    int minHeight, maxHeight;
    // Bin's width and height
    int binWidth, binHeight;

    private Type(int minWidth, int maxWidth, int minHeight, int maxHeight, int binWidth,
        int binHeight) {
      this.minWidth = minWidth;
      this.maxWidth = maxWidth;
      this.minHeight = minHeight;
      this.maxHeight = maxHeight;
      this.binWidth = binWidth;
      this.binHeight = binHeight;
    }
  }

  private Random random;

  public BerkeyWangGenerator() {
    random = new Random();
  }

  public BerkeyWangGenerator(long seed) {
    random = new Random(seed);
  }

  public List<Input> generateInstances(int count, int itemsCount, Type type) {
    return generateInstances(count, itemsCount, type.minWidth, type.maxWidth, type.minHeight,
        type.maxHeight, type.binWidth, type.binHeight);
  }

  public Input generateInstance(int itemsCount, Type type) {
    return generateInstance(itemsCount, type.minWidth, type.maxWidth, type.minHeight,
        type.maxHeight, type.binWidth, type.binHeight);
  }

  public List<Input> generateInstances(int count, int itemsCount, int minWidth, int maxWidth,
      int minHeight, int maxHeight, int binWidth, int binHeight) {
    ArrayList<Input> instances = new ArrayList<Input>(count);
    for (int i = 0; i < count; i++) {
      instances.add(generateInstance(itemsCount, minWidth, maxWidth, minHeight, maxHeight,
          binWidth, binHeight));
    }
    return instances;
  }

  public Input generateInstance(int itemsCount, int minWidth, int maxWidth, int minHeight, int maxHeight, int binWidth, int binHeight) {
    long seed = System.currentTimeMillis();
    random.setSeed(seed);
    ArrayList<Rectangle> items = new ArrayList<Rectangle>(itemsCount);
    for (int i = 0; i < itemsCount; i++) {
      items.add(new Rectangle(random.nextInt(maxWidth - minWidth + 1) + minWidth,
          random.nextInt(maxHeight - minHeight + 1) + minHeight));
    }
    return new Input(items, binWidth, binHeight, INPUT_IDENTIFIER_PREFIX + "random." + seed);
  }

  public static List<Input> getReferenceInstances(Type type) {
    return REFERENCE_INSTANCES.get(type);
  }

  private static final Map<Type, List<Input>> REFERENCE_INSTANCES;

  private static final String REFERENCE_INSTANCES_RESOURCE_NAME =
      "BerkeyWangReferenceInstances.txt";
  private static final int INSTANCES_PER_TYPE = 50;

  static {
    REFERENCE_INSTANCES = new HashMap<Type, List<Input>>();
    Scanner scanner = new Scanner(BerkeyWangGenerator.class.getResourceAsStream(
        REFERENCE_INSTANCES_RESOURCE_NAME));
    for (Type type : Type.values()) {
      ArrayList<Input> instances = new ArrayList<Input>(INSTANCES_PER_TYPE);
      for (int id = 0; id < INSTANCES_PER_TYPE; id++) {
        int width = scanner.nextInt();
        int itemsCount = scanner.nextInt();
        Rectangle items[] = new Rectangle[itemsCount];
        for (int i = 0; i < itemsCount; i++) {
          items[i] = new Rectangle(scanner.nextInt(), scanner.nextInt());
        }
        Input instance = new Input(items, width, width, INPUT_IDENTIFIER_PREFIX + "Class " +
            (type.ordinal() + 1) + "." + String.format("%02d", id + 1));
        instances.add(instance);
      }
      REFERENCE_INSTANCES.put(type, instances);
    }
  }
}
