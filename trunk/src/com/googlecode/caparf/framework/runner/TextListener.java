package com.googlecode.caparf.framework.runner;

import com.googlecode.caparf.framework.base.Algorithm;
import com.googlecode.caparf.framework.base.BaseInput;
import com.googlecode.caparf.framework.base.BaseOutput;
import com.googlecode.caparf.framework.base.Verdict;
import com.googlecode.caparf.framework.base.Verdict.Result;

/**
 * Simple implementation of {@link RunListener} that collects number of
 * correctly solved inputs and displays it after scenario is finished.
 *
 * @param <I> algorithm input
 * @param <O> algorithm output
 * @param <V> output verification verdict
 *
 * @author denis.nsc@gmail.com (Denis Nazarov)
 */
public class TextListener<I extends BaseInput, O extends BaseOutput> extends RunListener<I, O> {

  private long startMillis, finishMillis;
  private int total, valid;

  @Override
  public void scenarioRunStarted(Scenario<I, O> scenario) throws Exception {
    startMillis = System.currentTimeMillis();
    total = valid = 0;
    System.out.println("Starting scenario");
  }

  @Override
  public void scenarioRunFinished() throws Exception {
    finishMillis = System.currentTimeMillis();
    System.out.println(String.format("\nScenario finished in %.3f sec",
        (finishMillis - startMillis) * 1e-3));
    if (total == valid) {
      System.out.println("OK: all " + total + " runs were successfull");
    } else {
      System.out.println("ERROR: " + (total - valid) + " run of total " + total +
          " runs were not successfull");
    }
  }

  @Override
  public void testFinished(Algorithm<I, O> algorithm, I input, O output, Verdict verdict)
      throws Exception {
    total++;
    if (verdict.getResult() == Result.VALID_OUTPUT) {
      System.out.print('.');
      valid++;
    } else {
      System.out.print('E');
    }
  }
}
