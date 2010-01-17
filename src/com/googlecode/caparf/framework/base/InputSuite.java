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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Defines suite for algorithm inputs.
 *
 * @author denis.nsc@gmail.com (Denis Nazarov)
 */
public class InputSuite<I extends BaseInput<? extends BaseItem>> {

  /** List of algorithm inputs in this suite. */
  private ArrayList<I> algorithmInputs;

  /** Creates empty input suite. */
  public InputSuite() {
    algorithmInputs = new ArrayList<I>();
  }

  /**
   * Returns read-only list of all algorithm inputs in this suite.
   *
   * @return all algorithm inputs in this suite
   */
  public List<I> getAll() {
    return Collections.unmodifiableList(algorithmInputs);
  }

  /**
   * Adds single algorithm input to the suite.
   *
   * @param input input to add
   * @return this
   */
  public InputSuite<I> add(I input) {
    algorithmInputs.add(input);
    return this;
  }

  /**
   * Adds all algorithm inputs in the given suite to this suite
   *
   * @param inputs input suite to add
   * @return this
   */
  public InputSuite<I> addAll(InputSuite<? extends I> inputs) {
    algorithmInputs.addAll(inputs.getAll());
    return this;
  }

  /**
   * Adds all algorithm inputs in the given collection to the suite.
   *
   * @param inputs inputs to add
   * @return this
   */
  public InputSuite<I> addAll(Collection<? extends I> inputs) {
    algorithmInputs.addAll(inputs);
    return this;
  }

  /**
   * Adds all given algorithm inputs to the suite.
   *
   * @param inputs inputs to add
   * @return this
   */
  public InputSuite<I> addAll(I... inputs) {
    Collections.addAll(algorithmInputs, inputs);
    return this;
  }
}
