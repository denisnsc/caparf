package com.googlecode.caparf.framework.runner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.googlecode.caparf.framework.base.Algorithm;
import com.googlecode.caparf.framework.base.BaseInput;
import com.googlecode.caparf.framework.base.BaseOutput;
import com.googlecode.caparf.framework.base.BaseOutputVerdict;

/**
 * This class is used by Runner to notify Caparf of progress running scenario.
 */
public class RunNotifier<I extends BaseInput, O extends BaseOutput, V extends BaseOutputVerdict> {

  private final List<RunListener<I, O, V>> listeners = new ArrayList<RunListener<I, O, V>>();

  /** Internal use only. */
  public void addListener(RunListener<I, O, V> listener) {
    listeners.add(listener);
  }

  /** Internal use only. */
  public void removeListener(RunListener<I, O, V> listener) {
    listeners.remove(listener);
  }

  private abstract class SafeNotifier {
    void run() {
      for (Iterator<RunListener<I, O, V>> all = listeners.iterator(); all.hasNext();)
        try {
          notifyListener(all.next());
        } catch (Exception e) {
          e.printStackTrace();
          all.remove();
        }
    }

    abstract protected void notifyListener(RunListener<I, O, V> each) throws Exception;
  }

  /** Do not invoke. */
  public void fireScenarioRunStarted(final Scenario<I, O, V> scenario) {
    new SafeNotifier() {
      @Override
      protected void notifyListener(RunListener<I, O, V> each) throws Exception {
        each.scenarioRunStarted(scenario);
      };
    }.run();
  }

  /** Do not invoke. */
  public void fireScenarioRunFinished() {
    new SafeNotifier() {
      @Override
      protected void notifyListener(RunListener<I, O, V> each) throws Exception {
        each.scenarioRunFinished();
      };
    }.run();
  }

  /**
   * Invoke to tell listeners that an {@code algorithm} is about to be started
   * on {@code input}.
   */
  public void fireTestStarted(final Algorithm<I, O> algorithm, final I input) {
    new SafeNotifier() {
      @Override
      protected void notifyListener(RunListener<I, O, V> each) throws Exception {
        each.testStarted(algorithm, input);
      };
    }.run();
  }

  /**
   * Invoke to tell listeners that {@code algorithm} has solved {@code input} .
   */
  public void fireTestFinished(final Algorithm<I, O> algorithm, final I input, final O output,
      final V verdict) {
    new SafeNotifier() {
      @Override
      protected void notifyListener(RunListener<I, O, V> each) throws Exception {
        each.testFinished(algorithm, input, output, verdict);
      };
    }.run();
  }
}
