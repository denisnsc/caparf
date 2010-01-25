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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.googlecode.caparf.framework.util.CollectionUtil;
import com.googlecode.caparf.framework.util.ObjectUtil;

/**
 * Base class for cutting-and-packing algorithm's output. Key outputs properties
 * are:
 * <ol>
 * <li><b>Item placements</b>. Each output consists of item placements. These
 * can be points in 2D or 3D space, container identifier etc. ItemPlacements are
 * clonable and extends base class {@link BaseItemPlacement}.
 * <li><b>Objective function</b>. Each output can calculate the value of
 * objective function for the corresponding problem.
 * <li><b>Transformation</b>. Outputs can be transformed according to the given
 * transformation which is simply a many-to-one mapping between new and original
 * item placements. This can be extremely useful for various genetic algorithms.
 * <li><b>Cloning</b>. Outputs are clonable.
 * </ol>
 * <p>
 * Concrete output class must implement {@link #calculateObjectiveFunction()}
 * that will return the value of objective function to <b>minimize</b>. If the
 * objective for some problem is to maximize {@code f(...)} then one may return
 * {@code -f(...)} as value of objective function to minimize. <i>Note</i>, that
 * objective function is calculated every call. One can use helper class
 * {@link ObjectiveComparator} to compare values of objective functions
 * regardless of value type.
 * <p>
 * Output transformation changes existing instance of output according to the
 * given mapping. The most common use case for this is to permute item
 * placements. However, transformation can be more tricky like replacing second
 * item placement by first item placement ( the corresponding mapping is
 * {@code transformation = (1, 1, 3, 4, ...)}). Default implementation of
 * {@link #transform(List)} is simply applying transformation to the list of
 * item placements. It should be sufficient for most (if not all) output
 * classes.
 *
 * @param <T> item class
 *
 * @author denis.nsc@gmail.com (Denis Nazarov)
 *
 * @see com.googlecode.caparf.framework.spp2d.Output
 * @see com.googlecode.caparf.framework.items.RectanglePlacement
 */
public abstract class BaseOutput<T extends BaseItemPlacement> implements BaseCloneable {

  /** List of item placements. */
  protected List<T> placements;

  /**
   * Constructs output instance.
   *
   * @param placements item placements
   * @param identifier input identifier
   */
  public BaseOutput(List<T> placements) {
    this.placements = CollectionUtil.deepCopyOf(placements);
  }

  /**
   * Constructs output instance.
   *
   * @param placements item placements
   * @param identifier input identifier
   */
  public BaseOutput(T[] placements) {
    this.placements = CollectionUtil.deepCopyOf(placements);
  }

  /**
   * Calculates value of objective function to minimize.
   *
   * @return objective function value
   */
  public abstract Number calculateObjectiveFunction();

  /**
   * @return number of item placements in the output
   */
  public int getPlacementsCount() {
    return placements.size();
  }

  /**
   * Returns item placements. Order of elements matters. More precisely, the
   * order of elements corresponds to the order of items in the input. Resulting
   * list is unmodifiable, i.e. exception will be thrown if one tries to add or
   * remove elements from it. However, item placements contained in a list are
   * mutable.
   *
   * @return unmodifiable list of item placements
   */
  public List<T> getPlacements() {
    return Collections.unmodifiableList(placements);
  }

  /**
   * Transforms output according to {@code transformation}. Transformed output
   * will have exactly the same number of item placements as the given {@code
   * transformation}. {@code i}-th item placement in transformed output will be
   * equal to {@code transformation.get(i)}-th item placement in original
   * output.
   *
   * @param transformation items transformation
   */
  public void transform(List<Integer> transformation) {
    List<T> transformedPlacements = new ArrayList<T>(transformation.size());
    for (int placementId : transformation) {
      transformedPlacements.add(ObjectUtil.safeClone(placements.get(placementId)));
    }
    placements = transformedPlacements;
  }

  /**
   * Transforms output according to {@code transformation}. Transformed output
   * will have exactly the same number of item placements as the given {@code
   * transformation}. {@code i}-th item placement in transformed output will be
   * equal to {@code transformation[i]}-th item placement in original
   * output. Use this function to gain extra performance (comparing to
   * {@link #transform(List)}).
   *
   * @param transformation items transformation
   */
  public void transform(int[] transformation) {
    List<T> transformedPlacements = new ArrayList<T>(transformation.length);
    for (int i = 0; i < transformation.length; i++) {
      transformedPlacements.add(ObjectUtil.safeClone(placements.get(transformation[i])));
    }
    placements = transformedPlacements;
  }

  @Override
  public Object clone() {
    try {
      @SuppressWarnings("unchecked")
      BaseOutput<T> clone = (BaseOutput<T>) super.clone();
      clone.placements = CollectionUtil.deepCopyOf(placements);
      return clone;
    } catch (CloneNotSupportedException e) {
      // this shouldn't happen, since we are Cloneable
      throw new InternalError();
    }
  }
}
