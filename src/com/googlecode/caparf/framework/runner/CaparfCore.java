package com.googlecode.caparf.framework.runner;

import com.googlecode.caparf.framework.base.Algorithm;
import com.googlecode.caparf.framework.base.BaseInput;
import com.googlecode.caparf.framework.base.BaseOutput;
import com.googlecode.caparf.framework.base.BaseOutputVerdict;

/**
 * Main class for running caparf scenarios.
 * 
 * @author denis.nsc@gmail.com (Denis Nazarov)
 */
public class CaparfCore<I extends BaseInput, O extends BaseOutput,
    V extends BaseOutputVerdict> {

  private RunNotifier<I, O, V> notifier;

  public CaparfCore() {
    notifier = new RunNotifier<I, O, V>();
    notifier.addListener(new TextListener<I, O, V>());
  }
  
  /**
   * Runs the given {@code scenario}
   * 
   * @param scenario scenario to execute
   */
  public void run(Scenario<I, O, V> scenario) {
    notifier.fireScenarioRunStarted(scenario);
    for (I input : scenario.getInputs().getAll()) {
      for (Algorithm<I, O> algorithm : scenario.getAlgorithms()) {
        notifier.fireTestStarted(algorithm, input);
        O output = algorithm.solve(input);
        V verdict = scenario.getVerifier().verify(input, output);
        notifier.fireTestFinished(algorithm, input, output, verdict);
      }
    }
    notifier.fireScenarioRunFinished();
  }

  /**
   * Adds a listener to be notified as the scenario run.
   * 
   * @param listener the listener to add
   */
  public void addListener(RunListener<I, O, V> listener) {
    notifier.addListener(listener);
  }

  /**
   * Removes a listener.
   * 
   * @param listener the listener to remove
   */
  public void removeListener(RunListener<I, O, V> listener) {
    notifier.removeListener(listener);
  }
}
