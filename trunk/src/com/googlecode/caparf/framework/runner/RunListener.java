package com.googlecode.caparf.framework.runner;

import com.googlecode.caparf.framework.base.Algorithm;
import com.googlecode.caparf.framework.base.BaseInput;
import com.googlecode.caparf.framework.base.BaseItem;
import com.googlecode.caparf.framework.base.BaseItemPlacement;
import com.googlecode.caparf.framework.base.BaseOutput;
import com.googlecode.caparf.framework.base.Verdict;

/**
 * If you need to respond to the events during a scenario run, extend {@code
 * RunListener} and override the appropriate methods. If a listener throws an
 * exception while processing a test event, it will be removed for the remainder
 * of the scenario run.
 *
 * @author denis.nsc@gmail.com (Denis Nazarov)
 */
public class RunListener<I extends BaseInput<? extends BaseItem>,
    O extends BaseOutput<? extends BaseItemPlacement>> {

  /**
   * Called before scenario has been run.
   */
  public void scenarioRunStarted(Scenario<I, O> scenario) throws Exception {
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
  public void testFinished(Algorithm<I, O> algorithm, I input, O output, Verdict verdict)
      throws Exception {
  }
}
