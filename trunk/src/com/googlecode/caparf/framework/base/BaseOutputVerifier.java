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

/**
 * Base interface for verifier of cutting-and-packing algorithms' outputs.
 *
 * @param <I> algorithm input
 * @param <O> algorithm output
 *
 * @author denis.nsc@gmail.com (Denis Nazarov)
 */
public interface BaseOutputVerifier<I extends BaseInput<? extends BaseItem>,
    O extends BaseOutput<? extends BaseItemPlacement>> {

  /**
   * Verifies that the given {@code output} is valid.
   *
   * @param input algorithm input
   * @param output algorithm output
   * @return output verification verdict
   */
  Verdict verify(I input, O output);
}
