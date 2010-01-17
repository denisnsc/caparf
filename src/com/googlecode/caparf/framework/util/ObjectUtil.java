package com.googlecode.caparf.framework.util;

import com.googlecode.caparf.framework.base.BaseCloneable;

/**
 * Utilities for working with objects.
 *
 * @author denis.nsc@gmail.com (Denis Nazarov)
 */
public final class ObjectUtil {

  private ObjectUtil() {
  }

  /**
   * Clones the given {@code object}. This function returns the clone of the
   * same type as original object in contrast with {@link Object#clone()}.
   *
   * @param object object to clone
   * @return clone of {@code object}
   */
  public static <T extends BaseCloneable> T safeClone(T object) {
    // It is always safe to downcast clone to same type as original object
    @SuppressWarnings("unchecked")
    T clonedObject = (T) object.clone();
    return clonedObject;
  }
}
