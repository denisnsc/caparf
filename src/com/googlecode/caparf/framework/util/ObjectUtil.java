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
