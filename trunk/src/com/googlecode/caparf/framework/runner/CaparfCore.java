package com.googlecode.caparf.framework.runner;

import java.lang.management.ManagementFactory;

import com.googlecode.caparf.framework.base.Algorithm;
import com.googlecode.caparf.framework.base.BaseInput;
import com.googlecode.caparf.framework.base.BaseItem;
import com.googlecode.caparf.framework.base.BaseOutput;
import com.googlecode.caparf.framework.base.Verdict;
import com.googlecode.caparf.framework.runner.RunInformation;

/**
 * Main class for running caparf scenarios.
 *
 * @author denis.nsc@gmail.com (Denis Nazarov)
 */
public class CaparfCore<I extends BaseInput<? extends BaseItem>, O extends BaseOutput> {

  private RunNotifier<I, O> notifier;

  public CaparfCore() {
    notifier = new RunNotifier<I, O>();
    notifier.addListener(new TextListener<I, O>());
    configureJVM();
  }

  private void configureJVM() {
    if (!ManagementFactory.getThreadMXBean().isThreadCpuTimeSupported()) {
      System.err.println("ERROR: Java Vritual Machine does not support thread cpu time");
    }
    if (ManagementFactory.getThreadMXBean().isThreadCpuTimeEnabled()) {
      System.out.println("INFO: Thread cpu time is already enabled");
    } else {
      ManagementFactory.getThreadMXBean().setThreadCpuTimeEnabled(true);
      System.out.println("INFO: Thread cpu time has been enabled");
    }
  }

  /**
   * Runs the given {@code scenario}
   *
   * @param scenario scenario to execute
   */
  @SuppressWarnings("unchecked")
  public void run(Scenario<I, O> scenario) {
    notifier.fireScenarioRunStarted(scenario);
    for (I input : scenario.getInputs().getAll()) {
      for (Algorithm<I, O> algorithm : scenario.getAlgorithms()) {
        notifier.fireTestStarted(algorithm, input);
        RunInformation runInfo = new RunInformation();
        O output = Runner.run(algorithm, (I) input.clone(), scenario.getTimeLimit(), runInfo);
        Verdict verdict;
        if (runInfo.getResult() == RunInformation.RunResult.OK) {
          verdict = scenario.getVerifier().verify((I) input.clone(), output);
        } else {
          verdict = new Verdict();
          verdict.setResult(Verdict.Result.FAILED_TO_RUN);
        }
        verdict.setRunInformation(runInfo);
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
  public void addListener(RunListener<I, O> listener) {
    notifier.addListener(listener);
  }

  /**
   * Removes a listener.
   *
   * @param listener the listener to remove
   */
  public void removeListener(RunListener<I, O> listener) {
    notifier.removeListener(listener);
  }
}
