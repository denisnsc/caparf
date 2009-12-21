package com.googlecode.caparf.framework.runner;

import com.googlecode.caparf.framework.base.Algorithm;
import com.googlecode.caparf.framework.base.BaseInput;
import com.googlecode.caparf.framework.base.BaseOutput;
import com.googlecode.caparf.framework.base.BaseOutputVerdict;
import com.googlecode.caparf.framework.base.BaseOutputVerdict.Verdict;

public class TextListener<I extends BaseInput, O extends BaseOutput, V extends BaseOutputVerdict> extends
    RunListener<I, O, V> {

  private long startMillis, finishMillis;
  private int total, valid;

  @Override
  public void scenarioRunStarted(Scenario<I, O, V> scenario) throws Exception {
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
  public void testFinished(Algorithm<I, O> algorithm, I input, O output, V verdict)
      throws Exception {
    total++;
    if (verdict.getVerdict() == Verdict.VALID_OUTPUT) {
      System.out.print('.');
      valid++;
    } else {
      System.out.print('E');
    }
  }
}
