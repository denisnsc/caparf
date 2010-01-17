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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.googlecode.caparf.framework.base.Algorithm;
import com.googlecode.caparf.framework.base.BaseInput;
import com.googlecode.caparf.framework.base.BaseItem;
import com.googlecode.caparf.framework.base.BaseItemPlacement;
import com.googlecode.caparf.framework.base.BaseOutput;
import com.googlecode.caparf.framework.base.Verdict;

/**
 * This class is used by Runner to notify Caparf of progress running scenario.
 */
public class RunNotifier<I extends BaseInput<? extends BaseItem>,
    O extends BaseOutput<? extends BaseItemPlacement>> {

  private final List<RunListener<I, O>> listeners = new ArrayList<RunListener<I, O>>();

  /** Internal use only. */
  public void addListener(RunListener<I, O> listener) {
    listeners.add(listener);
  }

  /** Internal use only. */
  public void removeListener(RunListener<I, O> listener) {
    listeners.remove(listener);
  }

  private abstract class SafeNotifier {
    void run() {
      for (Iterator<RunListener<I, O>> all = listeners.iterator(); all.hasNext();)
        try {
          notifyListener(all.next());
        } catch (Exception e) {
          e.printStackTrace();
          all.remove();
        }
    }

    abstract protected void notifyListener(RunListener<I, O> each) throws Exception;
  }

  /** Do not invoke. */
  public void fireScenarioRunStarted(final Scenario<I, O> scenario) {
    new SafeNotifier() {
      @Override
      protected void notifyListener(RunListener<I, O> each) throws Exception {
        each.scenarioRunStarted(scenario);
      };
    }.run();
  }

  /** Do not invoke. */
  public void fireScenarioRunFinished() {
    new SafeNotifier() {
      @Override
      protected void notifyListener(RunListener<I, O> each) throws Exception {
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
      protected void notifyListener(RunListener<I, O> each) throws Exception {
        each.testStarted(algorithm, input);
      };
    }.run();
  }

  /**
   * Invoke to tell listeners that {@code algorithm} has solved {@code input} .
   */
  public void fireTestFinished(final Algorithm<I, O> algorithm, final I input, final O output,
      final Verdict verdict) {
    new SafeNotifier() {
      @Override
      protected void notifyListener(RunListener<I, O> each) throws Exception {
        each.testFinished(algorithm, input, output, verdict);
      };
    }.run();
  }
}
