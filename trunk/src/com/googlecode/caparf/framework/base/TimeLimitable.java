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
 * Interface that should be implemented by algorithms or lower bounds that want
 * to get time limit to be applied for their run. If time-limitable algorithm
 * (or lower bound) is run using {@link Runner} then
 * {@link #setTimeLimit(long)} will be called before computation starts.
 * Information about time limit can be used by algorithm (or lower bound) to
 * approximately plan how much time it is possible to spend on some part of
 * computation. However, it is better to implement interface
 * {@link Interruptible} also to get notified once computation exceeds the time
 * limit.
 *
 * @author denis.nsc@gmail.com (Denis Nazarov)
 * @see Interruptible
 * @see Runner
 */
public interface TimeLimitable {

  /**
   * Sets time limit for further computation in milliseconds. If {@code millis}
   * is equal to {@code 0} then it means that computation will not be limited in
   * time.
   *
   * @param millis time limit for further computation in milliseconds
   */
  public void setTimeLimit(long millis);
}
