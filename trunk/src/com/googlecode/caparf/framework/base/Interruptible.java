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

package com.googlecode.caparf.framework.base;

import com.googlecode.caparf.framework.runner.Runner;

/**
 * Interface that should be implemented by algorithms or lower bounds that can
 * interrupt their computations. If interruptible algorithm (or lower bound) is
 * run with time limit using {@link Runner} then {@link #interrupt()} will be
 * called after algorithm (or lower bound) exceeds the time limit. After that
 * {@link Runner#EXTRA_TIME_LIMIT} milliseconds will be given to algorithm (or
 * lower bound) to finish computations and return result.
 *
 * @author denis.nsc@gmail.com (Denis Nazarov)
 * @see Runner
 */
public interface Interruptible {

  /**
   * Interrupts current computations and asks to return result as soon as
   * possible.
   */
  public void interrupt();
}
