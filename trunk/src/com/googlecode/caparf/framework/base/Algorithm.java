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

/**
 * Base class for cutting-and-packing algorithms.
 *
 * @param <I> algorithm input class
 * @param <O> algorithm output class
 *
 * @author denis.nsc@gmail.com (Denis Nazarov)
 */
public abstract class Algorithm<I extends BaseInput<? extends BaseItem>,
    O extends BaseOutput<? extends BaseItemPlacement>> {

  /**
   * Runs algorithm for the given {@code input} and returns {@code output}.
   *
   * @param input algorithm input
   * @return algorithm output
   */
  public abstract O solve(I input);

  /**
   * Returns display user-visible name which is equal to simple name of algorithm
   * class by default.
   *
   * @return user-understandable label
   */
  public String getDisplayName() {
    return this.getClass().getSimpleName();
  }

  @Override
  public String toString() {
    return getDisplayName();
  }
}
