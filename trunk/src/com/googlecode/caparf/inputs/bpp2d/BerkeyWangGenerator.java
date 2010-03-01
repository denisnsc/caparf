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
import java.util.Collections;
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

  public static final Type CLASS_I   = new Type(1, 10,  1, 10,  10,  10);
  public static final Type CLASS_II  = new Type(1, 10,  1, 10,  30,  30);
  public static final Type CLASS_III = new Type(1, 35,  1, 35,  40,  40);
  public static final Type CLASS_IV  = new Type(1, 35,  1, 35,  100, 100);
  public static final Type CLASS_V   = new Type(1, 100, 1, 100, 100, 100);
  public static final Type CLASS_VI  = new Type(1, 100, 1, 100, 300, 300);

  /**
   * Instance type described by items width and height distribution, width and
   * height of the bin.
   */
  public static class Type {
    // Items' width is uniformly random in [minWidth .. maxWidth]
    protected int minWidth, maxWidth;
    // Items' height is uniformly random in [minHeight .. maxHeight]
    protected int minHeight, maxHeight;
    // Bin's width and height
    protected int binWidth, binHeight;

    /**
     * Creates instance of {@code Type} by the given parameters. Widths of
     * generated items for this type will be uniformly distributed in
     * <code>[minWidth .. maxWidth]</code> and height will be respectively in
     * <code>[minHeight .. maxHeight]</code>. Width and height of the bin will
     * be equal to <code>binWidth</code> and <code>binHeight</code>
     * correspondingly.
     */
    public Type(int minWidth, int maxWidth, int minHeight, int maxHeight, int binWidth,
        int binHeight) {
      this.minWidth = minWidth;
      this.maxWidth = maxWidth;
      this.minHeight = minHeight;
      this.maxHeight = maxHeight;
      this.binWidth = binWidth;
      this.binHeight = binHeight;
    }
  }

  /** Random generator that will be used in inputs generation. */
  private Random random;
  /** Random generator that will be used to generate seeds. */
  private Random seedGenerator;

  public BerkeyWangGenerator() {
    random = new Random();
    seedGenerator = new Random();
  }

  /**
   * Generates {@code code} inputs with {@code itemsCount} items of {@code type}
   * type.
   *
   * @param count number of inputs to generate
   * @param itemsCount number of items to generate (per one input)
   * @param type type of items to generate
   * @return {@code code} inputs with {@code itemsCount} items of {@code type}
   *         type
   */
  public List<Input> generateInstances(int count, int itemsCount, Type type) {
    return generateInstances(count, itemsCount, type, seedGenerator.nextLong());
  }

  /**
   * Generates {@code code} inputs with {@code itemsCount} items of {@code type}
   * type. List of inputs generated with the same {@code seed} by this method
   * will be identical.
   *
   * @param count number of inputs to generate
   * @param itemsCount number of items to generate (per one input)
   * @param type type of items to generate
   * @param seed the initial seed
   * @return {@code code} inputs with {@code itemsCount} items of {@code type}
   *         type
   */
  public List<Input> generateInstances(int count, int itemsCount, Type type, long seed) {
    seedGenerator.setSeed(seed);
    ArrayList<Input> instances = new ArrayList<Input>(count);
    for (int i = 0; i < count; i++) {
      instances.add(generateInstance(itemsCount, type, seedGenerator.nextLong()));
    }
    return instances;
  }

  /**
   * Generates single input with {@code itemsCount} items of {@code type} type.
   *
   * @param itemsCount number of items to generate (per one input)
   * @param type type of items to generate
   * @return {@code code} inputs with {@code itemsCount} items of {@code type}
   *         type
   */
  public Input generateInstance(int itemsCount, Type type) {
    return generateInstance(itemsCount, type, seedGenerator.nextLong());
  }

  /**
   * Generates single input with {@code itemsCount} items of {@code type} type.
   * Inputs generated with the same {@code seed} by this method will be
   * identical.
   *
   * @param itemsCount number of items to generate (per one input)
   * @param type type of items to generate
   * @param seed the initial seed
   * @return {@code code} inputs with {@code itemsCount} items of {@code type}
   *         type
   */
  public Input generateInstance(int itemsCount, Type type, long seed) {
    random.setSeed(seed);
    ArrayList<Rectangle> items = new ArrayList<Rectangle>(itemsCount);
    for (int i = 0; i < itemsCount; i++) {
      items.add(new Rectangle(random.nextInt(type.maxWidth - type.minWidth + 1) + type.minWidth,
          random.nextInt(type.maxHeight - type.minHeight + 1) +
          type.minHeight));
    }
    return new Input(items, type.binWidth, type.binHeight, INPUT_IDENTIFIER_PREFIX + "random." +
        seed);
  }

  /**
   * @return reference instances corresponding to the given {@code type}
   */
  public static List<Input> getReferenceInstances(Type type) {
    return REFERENCE_INSTANCES.get(type);
  }

  /**
   * @return all reference instances
   */
  public static List<Input> getReferenceInstances() {
    ArrayList<Input> result = new ArrayList<Input>();
    for (List<Input> instances : REFERENCE_INSTANCES.values()) {
      result.addAll(instances);
    }
    return result;
  }

  private static final Map<Type, List<Input>> REFERENCE_INSTANCES;

  private static final String REFERENCE_INSTANCES_RESOURCE_NAME =
      "BerkeyWangReferenceInstances.txt";
  private static final int INSTANCES_PER_TYPE = 50;

  static {
    REFERENCE_INSTANCES = new HashMap<Type, List<Input>>();
    Scanner scanner = new Scanner(BerkeyWangGenerator.class.getResourceAsStream(
        REFERENCE_INSTANCES_RESOURCE_NAME));
    List<Type> allTypes = new ArrayList<Type>();
    Collections.addAll(allTypes, CLASS_I, CLASS_II, CLASS_III, CLASS_IV, CLASS_V, CLASS_VI);
    for (int j = 0; j < allTypes.size(); j++) {
      Type type = allTypes.get(j);
      ArrayList<Input> instances = new ArrayList<Input>(INSTANCES_PER_TYPE);
      for (int id = 0; id < INSTANCES_PER_TYPE; id++) {
        int width = scanner.nextInt();
        int itemsCount = scanner.nextInt();
        Rectangle items[] = new Rectangle[itemsCount];
        for (int i = 0; i < itemsCount; i++) {
          items[i] = new Rectangle(scanner.nextInt(), scanner.nextInt());
        }
        Input instance = new Input(items, width, width, INPUT_IDENTIFIER_PREFIX + "Class " +
            (j + 1) + "." + String.format("%02d", id + 1));
        instances.add(instance);
      }
      REFERENCE_INSTANCES.put(type, instances);
    }
  }
}
