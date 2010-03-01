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
 * Test instances generator proposed by Martello and Vigo. For the reference
 * look at <a href="http://www.jstor.org/pss/2634676">Exact Solution of the
 * Two-Dimensional Finite Bin Packing Problem (original article)</a> or at <a
 * href="http://www.or.deis.unibo.it/research_pages/ORinstances/lmv99.ps">
 * Heuristic and Metaheuristic Approaches for a Class of Two-Dimensional Bin
 * Packing Problems</a>.
 * <p>
 * <i>Note</i>: Implementation details for classes V-VII are not clear, only
 * classes I-IV are implemented.
 *
 * @author denis.nsc@gmail.com (Denis Nazarov)
 */
//TODO(dnazarov): implement classes V-VII once all details will be clear
public class MartelloVigoGenerator {

  public static final String INPUT_IDENTIFIER_PREFIX = "bpp2d.Martello and Vigo.";

  /** Bin width, which is the same for all instances. */
  public static final int BIN_WIDTH = 100;
  /** Bin height, which is the same for all instances. */
  public static final int BIN_HEIGHT = 100;

  // Reference item types
  public static final ItemType ITEM_TYPE_I =
      new ItemType(BIN_WIDTH * 2 / 3, BIN_WIDTH, 1, BIN_HEIGHT / 2);
  public static final ItemType ITEM_TYPE_II =
      new ItemType(1, BIN_WIDTH / 2, BIN_HEIGHT * 2 / 3, BIN_HEIGHT);
  public static final ItemType ITEM_TYPE_III =
      new ItemType(BIN_WIDTH / 2, BIN_WIDTH, BIN_HEIGHT / 2, BIN_HEIGHT);
  public static final ItemType ITEM_TYPE_IV =
      new ItemType(1, BIN_WIDTH / 2, 1, BIN_HEIGHT / 2);

  // Reference classes
  public static final Type CLASS_I   = Type.createReferenceType(7, 1, 1, 1);
  public static final Type CLASS_II  = Type.createReferenceType(1, 7, 1, 1);
  public static final Type CLASS_III = Type.createReferenceType(1, 1, 7, 1);
  public static final Type CLASS_IV  = Type.createReferenceType(1, 1, 1, 7);

  /** Item type described by its width and height distribution. */
  public static class ItemType {
    // Items' width is uniformly random in [minWidth .. maxWidth]
    protected int minWidth, maxWidth;
    // Items' height is uniformly random in [minHeight .. maxHeight]
    protected int minHeight, maxHeight;

    /**
     * Creates instance of {@code ItemType} by the given parameters. Widths of
     * generated items of this type will be uniformly distributed in
     * <code>[minWidth .. maxWidth]</code> and height will be respectively in
     * <code>[minHeight .. maxHeight]</code>.
     */
    public ItemType(int minWidth, int maxWidth, int minHeight, int maxHeight) {
      this.minWidth = minWidth;
      this.maxWidth = maxWidth;
      this.minHeight = minHeight;
      this.maxHeight = maxHeight;
    }
  }

  /**
   * Item type with weight corresponding to the probability that item of this
   * type will be created. Probability of generating item of particular type is
   * proportional to weight.
   */
  public static class ItemTypeWithWeight {
    /** Item type. */
    protected ItemType itemType;
    /**
     * Weight that corresponds to the probability that item of this type will be
     * created.
     */
    protected double weight;

    /** Creates instance of {@code ItemType} by the given parameters. */
    public ItemTypeWithWeight(ItemType itemType, double weight) {
      this.itemType = itemType;
      this.weight = weight;
    }
  }

  /** Instance type described by items types with weights. */
  public static class Type {
    /** Item types with weights. */
    protected ItemTypeWithWeight[] itemTypes;
    /** Sum of all weights. */
    protected double weightSum;

    /**
     * Creates instance of {@code Type} that will consists of the given {@code
     * itemType}.
     *
     * @param itemTypes items types with weights
     */
    public Type(ItemTypeWithWeight... itemTypes) {
      this.itemTypes = itemTypes;
      this.weightSum = 0;
      for (ItemTypeWithWeight itemType : this.itemTypes) {
        weightSum += itemType.weight;
      }
    }

    /**
     * Creates instance of {@code Type} that corresponds to reference classes
     * proposed by Martello and Vigo.
     */
    public static Type createReferenceType(double w1, double w2, double w3, double w4) {
      return new Type(new ItemTypeWithWeight(MartelloVigoGenerator.ITEM_TYPE_I, w1),
          new ItemTypeWithWeight(MartelloVigoGenerator.ITEM_TYPE_II, w2),
          new ItemTypeWithWeight(MartelloVigoGenerator.ITEM_TYPE_III, w3),
          new ItemTypeWithWeight(MartelloVigoGenerator.ITEM_TYPE_IV, w4));
    }
  }

  /** Random generator that will be used in inputs generation. */
  private Random random;
  /** Random generator that will be used to generate seeds. */
  private Random seedGenerator;

  public MartelloVigoGenerator() {
    random = new Random();
    seedGenerator = new Random();
  }

  /**
   * Generates {@code code} inputs of {@code type} type with {@code itemsCount}
   * items.
   *
   * @param count number of inputs to generate
   * @param itemsCount number of items to generate (per one input)
   * @param type type of instance to generate
   * @return {@code code} inputs of {@code type} type with {@code itemsCount}
   *         items
   */
  public List<Input> generateInstances(int count, int itemsCount, Type type) {
    return generateInstances(count, itemsCount, type, seedGenerator.nextLong());
  }

  /**
   * Generates {@code code} inputs of {@code type} type with {@code itemsCount}
   * items. List of inputs generated with the same {@code seed} by this method
   * will be identical.
   *
   * @param count number of inputs to generate
   * @param itemsCount number of items to generate (per one input)
   * @param type type of instance to generate
   * @param seed the initial seed
   * @return {@code code} inputs of {@code type} type with {@code itemsCount}
   *         items
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
   * Generates single input of {@code type} type with {@code itemsCount} items.
   *
   * @param itemsCount number of items to generate (per one input)
   * @param type type of instance to generate
   * @return {@code code} inputs of {@code type} type with {@code itemsCount}
   *         items
   */
  public Input generateInstance(int itemsCount, Type type) {
    return generateInstance(itemsCount, type, seedGenerator.nextLong());
  }

  /**
   * Generates single input of {@code type} type with {@code itemsCount} items.
   * Inputs generated with the same {@code seed} by this method will be
   * identical.
   *
   * @param itemsCount number of items to generate (per one input)
   * @param type type of instance to generate
   * @param seed the initial seed
   * @return {@code code} inputs of {@code type} type with {@code itemsCount}
   *         items
   */
  public Input generateInstance(int itemsCount, Type type, long seed) {
    random.setSeed(seed);
    ArrayList<Rectangle> items = new ArrayList<Rectangle>(itemsCount);
    for (int i = 0; i < itemsCount; i++) {
      double x = random.nextDouble() * type.weightSum;
      int itemTypeId = 0;
      while (type.itemTypes[itemTypeId].weight >= x && itemTypeId < type.itemTypes.length - 1) {
        x -= type.itemTypes[itemTypeId++].weight;
      }
      ItemType itemType = type.itemTypes[itemTypeId].itemType;
      items.add(new Rectangle(
          random.nextInt(itemType.maxWidth - itemType.minWidth + 1) + itemType.minWidth,
          random.nextInt(itemType.maxHeight - itemType.minHeight + 1) + itemType.minHeight));
    }
    return new Input(items, BIN_WIDTH, BIN_HEIGHT, INPUT_IDENTIFIER_PREFIX + "random." +  seed);
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
      "MartelloVigoReferenceInstances.txt";
  private static final int INSTANCES_PER_TYPE = 50;

  static {
    REFERENCE_INSTANCES = new HashMap<Type, List<Input>>();
    Scanner scanner = new Scanner(BerkeyWangGenerator.class.getResourceAsStream(
        REFERENCE_INSTANCES_RESOURCE_NAME));
    List<Type> allTypes = new ArrayList<Type>();
    Collections.addAll(allTypes, CLASS_I, CLASS_II, CLASS_III, CLASS_IV);
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
