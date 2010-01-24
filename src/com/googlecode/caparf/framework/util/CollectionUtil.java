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
    for (int i = 0; i < items.size(); i++) {
      result.add(ObjectUtil.safeClone(items.get(i)));
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
    for (int i = 0; i < items.length; i++) {
      result.add(ObjectUtil.safeClone(items[i]));
    }
    return result;
  }
}
