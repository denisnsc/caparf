package com.googlecode.caparf.tests.spp2d;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import com.googlecode.caparf.framework.spp2d.Input;
import com.googlecode.caparf.framework.spp2d.InputReader;
import com.googlecode.caparf.framework.spp2d.Input.Rectangle;

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
public class BerkeyWangGenerator extends InputReader {

  public enum Type {
    CLASS_I   (1, 10,  1, 10,  10 ),
    CLASS_II  (1, 10,  1, 10,  30 ),
    CLASS_III (1, 35,  1, 35,  40 ),
    CLASS_IV  (1, 35,  1, 35,  100),
    CLASS_V   (1, 100, 1, 100, 100),
    CLASS_VI  (1, 100, 1, 100, 300);

    // Items' height is uniformly random in [minHeight .. maxHeight]
    int minHeight, maxHeight;
    // Items' width is uniformly random in [minWidth .. maxWidth]
    int minWidth, maxWidth;
    // Strip width
    int stripWidth;

    private Type(int minHeight, int maxHeight, int minWidth, int maxWidth, int width) {
      this.minHeight = minHeight;
      this.maxHeight = maxHeight;
      this.minWidth = minWidth;
      this.maxWidth = maxWidth;
      this.stripWidth = width;
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
    return generateInstances(count, itemsCount, type.minHeight, type.maxHeight, type.minWidth,
        type.maxWidth, type.stripWidth);
  }

  public Input generateInstance(int itemsCount, Type type) {
    return generateInstance(itemsCount, type.minHeight, type.maxHeight, type.minWidth,
        type.maxWidth, type.stripWidth);
  }

  public List<Input> generateInstances(int count, int itemsCount, int minHeight, int maxHeight,
      int minWidth, int maxWidth, int width) {
    ArrayList<Input> instances = new ArrayList<Input>(count);
    for (int i = 0; i < count; i++) {
      instances.add(generateInstance(itemsCount, minHeight, maxHeight, minWidth, maxWidth, width));
    }
    return instances;
  }

  public Input generateInstance(int itemsCount, int minHeight, int maxHeight, int minWidth,
      int maxWidth, int width) {
    ArrayList<Rectangle> items = new ArrayList<Rectangle>(itemsCount);
    for (int i = 0; i < itemsCount; i++) {
      items.add(new Rectangle(random.nextInt(maxHeight - minHeight + 1) + minHeight,
          random.nextInt(maxWidth - minWidth + 1) + minWidth));
    }
    return new Input(items, width);
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
        int stripWidth = scanner.nextInt();
        int itemsCount = scanner.nextInt();
        int widths[] = new int[itemsCount];
        int heights[] = new int[itemsCount];
        for (int i = 0; i < itemsCount; i++) {
          widths[i] = scanner.nextInt();
          heights[i] = scanner.nextInt();
        }
        Input instance = new Input(widths, heights, stripWidth);
        instance.setDescription("Berkey and Wang instance, class " + type + ", id = " + (id + 1));
        instances.add(instance);
      }     
      REFERENCE_INSTANCES.put(type, instances);
    }
  }
}
