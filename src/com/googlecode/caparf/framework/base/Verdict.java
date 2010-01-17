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

import com.googlecode.caparf.framework.runner.RunInformation;

/**
 * Output verification verdict.
 *
 * @author denis.nsc@gmail.com (Denis Nazarov)
 */
public class Verdict {

  /** Possible results for output verification. */
  public enum Result {
    /** Output produced by algorithm is valid. */
    VALID_OUTPUT,
    /** Output produced by algorithm is invalid. */
    INVALID_OUTPUT,
    /** Algorithm failed to produce output. */
    FAILED_TO_RUN
  }

  /** Output verification result. */
  private Result result;

  /** Output verification comment. */
  private String comment;

  /** Information about algorithm execution. */
  private RunInformation runInformation;

  /**
   * @return output verification result
   */
  public Result getResult() {
    return result;
  }

  /**
   * Sets output verification result.
   *
   * @param result output verification result
   */
  public void setResult(Result result) {
    this.result = result;
  }

  /**
   * @return output verification comment
   */
  public String getComment() {
    return comment;
  }

  /**
   * Sets output verification comment.
   *
   * @param comment output verification comment
   */
  public void setComment(String comment) {
    this.comment = comment;
  }

  /**
   * @return information about algorithm execution
   */
  public RunInformation getRunInformation() {
    return runInformation;
  }

  /**
   * Sets information about algorithm execution.
   *
   * @param runInformation information about algorithm execution
   */
  public void setRunInformation(RunInformation runInformation) {
    this.runInformation = runInformation;
  }

  @Override
  public String toString() {
    return getResult() + ": " + getComment();
  }
}
