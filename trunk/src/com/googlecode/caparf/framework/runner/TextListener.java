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

package com.googlecode.caparf.framework.runner;

import com.googlecode.caparf.framework.base.Algorithm;
import com.googlecode.caparf.framework.base.BaseInput;
import com.googlecode.caparf.framework.base.BaseItem;
import com.googlecode.caparf.framework.base.BaseItemPlacement;
import com.googlecode.caparf.framework.base.BaseOutput;
import com.googlecode.caparf.framework.base.Verdict;

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
public class TextListener<I extends BaseInput<? extends BaseItem>,
    O extends BaseOutput<? extends BaseItemPlacement>> extends RunListener<I, O> {

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
    switch (verdict.getResult()) {
      case VALID_OUTPUT:
        System.out.print('.');
        valid++;
        break;
      case INVALID_OUTPUT:
        System.out.print('E');
        break;
      case FAILED_TO_RUN:
        System.out.print('F');
        break;
    }
  }
}
