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
public class InputSuite<I extends BaseInput> {

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
   * Adds all algorithms input in the given suite to this suite
   *
   * @param inputs input suite to add
   * @return this
   */
  public InputSuite<I> addAll(InputSuite<? extends I> inputs) {
    algorithmInputs.addAll(inputs.getAll());
    return this;
  }

  /**
   * Adds all algorithms input in the given collection to the suite.
   *
   * @param inputs inputs to add
   * @return this
   */
  public InputSuite<I> addAll(Collection<? extends I> inputs) {
    algorithmInputs.addAll(inputs);
    return this;
  }

  /**
   * Add all given algorithm inputs to the suite.
   *
   * @param inputs inputs to add
   * @return this
   */
  public InputSuite<I> addAll(I... inputs) {
    Collections.addAll(algorithmInputs, inputs);
    return this;
  }
}
