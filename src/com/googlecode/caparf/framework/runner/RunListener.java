package com.googlecode.caparf.framework.runner;

import com.googlecode.caparf.framework.base.Algorithm;
import com.googlecode.caparf.framework.base.BaseInput;
import com.googlecode.caparf.framework.base.BaseOutput;
import com.googlecode.caparf.framework.base.BaseOutputVerdict;

/**
 * If you need to respond to the events during a scenario run, extend {@code
 * RunListener} and override the appropriate methods. If a listener throws an
 * exception while processing a test event, it will be removed for the remainder
 * of the test run.
 *
 * @author denis.nsc@gmail.com (Denis Nazarov)
 */
public class RunListener<I extends BaseInput, O extends BaseOutput, V extends BaseOutputVerdict> {

  /**
   * Called before scenario has been run.
   */
  public void scenarioRunStarted(Scenario<I, O, V> scenario) throws Exception {
  }

  /**
   * Called when scenario is finished.
   */
  public void scenarioRunFinished() throws Exception {
  }

  /**
   * Called when an {@code algorithm} is about to be started on {@code input}.
   */
  public void testStarted(Algorithm<I, O> algorithm, I input) throws Exception {
  }

  /**
   * Called when an {@code algorithm} has solved {@code input}, whether it
   * succeeds or fails.
   */
  public void testFinished(Algorithm<I, O> algorithm, I input, O output, V verdict)
      throws Exception {
  }
}
