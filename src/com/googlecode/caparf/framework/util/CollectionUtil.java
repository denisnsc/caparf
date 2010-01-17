package com.googlecode.caparf.framework.util;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.caparf.framework.base.BaseCloneable;

/**
 * Utilities for working with collections.
 *
 * @author denis.nsc@gmail.com (Denis Nazarov)
 */
public class CollectionUtil {

  private CollectionUtil() {
  }

  /**
   * Makes deep copy of the given {@code items} by cloning all elements of
   * original list. If you need just a shallow copy then use
   * {@link ArrayList#clone()}.
   *
   * @param items list to deep copy
   * @return deep copy of {@code items}
   */
  public static <T extends BaseCloneable> List<T> deepCopyOf(List<T> items) {
    List<T> result = new ArrayList<T>(items.size());
    for (T item : items) {
      result.add(ObjectUtil.safeClone(item));
    }
    return result;
  }

  /**
   * Makes deep copy of the given {@code items} by cloning all given items. If
   * you need just a shallow copy then use {@link ArrayList#clone()}.
   *
   * @param items items to deep copy
   * @return deep copy of {@code items}
   */
  public static <T extends BaseCloneable> List<T> deepCopyOf(T... items) {
    List<T> result = new ArrayList<T>(items.length);
    for (T item : items) {
      result.add(ObjectUtil.safeClone(item));
    }
    return result;
  }
}
