package com.googlecode.caparf.framework.runner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.googlecode.caparf.framework.base.Algorithm;
import com.googlecode.caparf.framework.base.BaseInput;
import com.googlecode.caparf.framework.base.InputSuite;
import com.googlecode.caparf.framework.base.BaseOutput;
import com.googlecode.caparf.framework.base.BaseOutputVerdict;
import com.googlecode.caparf.framework.base.BaseOutputVerifier;

/**
 * Defines scenario to be executed by caparf.
 *
 * @author denis.nsc@gmail.com (Denis Nazarov)
 */
public class Scenario<I extends BaseInput, O extends BaseOutput, V extends BaseOutputVerdict> {

  private List<Algorithm<I, O>> algorithms;
  private InputSuite<I> inputs;
  private BaseOutputVerifier<I, O, V> verifier;

  public Scenario() {
    algorithms = new ArrayList<Algorithm<I, O>>();
    inputs = new InputSuite<I>();
    verifier = null;
  }

  public List<Algorithm<I, O>> getAlgorithms() {
    return algorithms;
  }

  public InputSuite<I> getInputs() {
    return inputs;
  }

  public BaseOutputVerifier<I, O, V> getVerifier() {
    return verifier;
  }

  public void addAlgorithm(Algorithm<I, O> algorithm) {
    this.algorithms.add(algorithm);
  }

  public void addAlgorithms(List<? extends Algorithm<I, O>> algorithms) {
    this.algorithms.addAll(algorithms);
  }

  public <T extends Algorithm<I, O>> void addAlgorithms(T... algorithms) {
    Collections.addAll(this.algorithms, algorithms);
  }

  public void addInput(I input) {
    this.inputs.add(input);
  }

  public void addInputSuite(InputSuite<? extends I> suite) {
    this.inputs.addAll(suite.getAll());
  }

  public void addInputs(List<? extends I> inputs) {
    this.inputs.addAll(inputs);
  }

  public <T extends I> void addInputs(T... inputs) {
    this.inputs.addAll(inputs);
  }

  public void setVerifier(BaseOutputVerifier<I, O, V> verifier) {
    this.verifier = verifier;
  }
}
