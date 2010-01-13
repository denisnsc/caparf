package com.googlecode.caparf.framework.base;

import java.util.Comparator;

/**
 * Comparator for objective function values.
 *
 * @author denis.nsc@gmail.com (Denis Nazarov)
 */
public class ObjectiveComparator implements Comparator<Number> {

  /** Largest relative error in comparing doubles. */
  public static final double EPS = 1e-9;

  /** Singleton instance of comparator. */
  private static final ObjectiveComparator singleton = new ObjectiveComparator();

  private ObjectiveComparator() {
  }

  /**
   * @return singleton instance of {@code ObjectiveComparator}
   */
  public static ObjectiveComparator getSingleton() {
    return singleton;
  }

  @Override
  public int compare(Number l, Number r) {
    double lvalue = l.doubleValue();
    double rvalue = r.doubleValue();
    double diff = (lvalue - rvalue) / (Math.abs(lvalue) + Math.abs(rvalue));
    return (diff > EPS) ? 1 : ((diff < -EPS) ? -1 : 0);
  }
}
