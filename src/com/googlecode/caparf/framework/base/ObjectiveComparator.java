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
