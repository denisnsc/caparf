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

import com.googlecode.caparf.framework.runner.Scenario;
import com.googlecode.caparf.framework.util.CollectionUtil;
import com.googlecode.caparf.framework.util.ObjectUtil;
import com.googlecode.caparf.inputs.bpp2d.BerkeyWangGenerator;

/**
 * Base class for cutting-and-packing algorithm's input. Key inputs properties
 * are:
 * <ol>
 * <li><b>Identifier</b>. Each input must have unique (within {@link Scenario})
 * identifier. No identifier within single {@link Scenario} should be a prefix
 * of another identifier. Input identifier is used for grouping inputs.
 * <li><b>Items</b>. Each input consists of items. These can be rectangles,
 * circles, boxes etc. Items are clonable and extends base class
 * {@link BaseItem}.
 * <li><b>Transformation</b>. Inputs can be transformed according to the given
 * transformation which is simply a many-to-one mapping between new and original
 * items. This can be extremely useful for various genetic algorithms.
 * <li><b>Cloning</b>. Inputs are clonable.
 * </ol>
 * <p>
 * Use the following convention to generate input identifiers. First part of
 * identifier is problem name, second part is source of input, other parts are
 * optional. For example, consider {@link BerkeyWangGenerator} that has 50
 * inputs in each of 6 defined classes and sample identifier "<code>spp2d.Berkey
 * and Wang.Class 3.08</code>". {@code spp2d} is the problem name, {@code Berkey
 * and Wang} is the source.
 * <p>
 * One needs to define item class for new problem extending {@link BaseItem}. If
 * item class consists only of primitive types then there is no need to
 * implement your own clone logic. Otherwise one needs to override
 * {@link BaseItem#clone()} and implement clone logic for new introduced fields
 * (look at {@link Object#clone()} for the reference). The same applies to input
 * class since it also should be cloneable.
 * <p>
 * Input transformation changes existing instance of input according to the
 * given mapping. The most common use case for this is to permute items.
 * However, transformation can be more tricky like replacing second item by
 * first item ( the corresponding mapping is {@code transformation = (1, 1, 3,
 * 4, ...)}). Default implementation of {@link #transform(List)} is simply
 * applying transformation to the list of items. It should be sufficient for
 * most (if not all) input classes.
 *
 * @param <T> item class
 *
 * @author denis.nsc@gmail.com (Denis Nazarov)
 *
 * @see com.googlecode.caparf.framework.spp2d.Input
 * @see com.googlecode.caparf.framework.items.Rectangle
 * @see com.googlecode.caparf.inputs.bpp2d.BerkeyWangGenerator
 */
public abstract class BaseInput<T extends BaseItem> implements BaseCloneable {

  /** Identifier of this input. */
  private final String identifier;

  /** List of items. */
  private List<T> items;

  /**
   * Constructs input instance.
   *
   * @param items input items
   * @param identifier input identifier
   */
  public BaseInput(List<T> items, String identifier) {
    validateIdentifier(identifier);
    this.identifier = identifier;
    this.items = CollectionUtil.deepCopyOf(items);
  }

  /**
   * Constructs input instance.
   *
   * @param items input items
   * @param identifier input identifier
   */
  public BaseInput(T[] items, String identifier) {
    validateIdentifier(identifier);
    this.identifier = identifier;
    this.items = CollectionUtil.deepCopyOf(items);
  }

  /** Checks that {@code identifier} is valid. */
  private void validateIdentifier(String identifier) {
    if (identifier.isEmpty()) {
      throw new IllegalArgumentException("Identifier must be non-empty");
    }
    if (identifier.startsWith(".")) {
      throw new IllegalArgumentException("Identifier must not start with dot");
    }
    if (identifier.endsWith(".")) {
      throw new IllegalArgumentException("Identifier must not end with dot");
    }
    if (identifier.indexOf("..") != -1) {
      throw new IllegalArgumentException("All parts of identifier must be non-empty");
    }
  }

  /**
   * Returns input identifier which is used for grouping inputs. Identifier
   * consists of several parts separated by dots.
   *
   * @return input identifier
   */
  public final String getIdentifier() {
    return identifier;
  }

  /**
   * @return number of items in the input
   */
  public int getItemsCount() {
    return items.size();
  }

  /**
   * Returns all input items. Order of items matters. Resulting list is
   * unmodifiable, i.e. exception will be thrown if one tries to add or remove
   * elements from it. However, items contained in a list are mutable.
   *
   * @return unmodifiable list of items
   */
  public List<T> getItems() {
    return Collections.unmodifiableList(items);
  }

  /**
   * Transforms input according to {@code transformation}. Transformed input
   * will have exactly the same number of items as the given {@code
   * transformation}. {@code i}-th item in transformed input will be equal to
   * {@code transformation.get(i)}-th item in original input.
   *
   * @param transformation items transformation
   */
  public void transform(List<Integer> transformation) {
    List<T> transformedItems = new ArrayList<T>(transformation.size());
    for (int itemId : transformation) {
      transformedItems.add(ObjectUtil.safeClone(items.get(itemId)));
    }
    items = transformedItems;
  }

  /**
   * Transforms input according to {@code transformation}. Transformed input
   * will have exactly the same number of items as the given {@code
   * transformation}. {@code i}-th item in transformed input will be equal to
   * {@code transformation[i]}-th item in original input. Use this function to
   * gain extra performance (comparing to {@link #transform(List)}).
   *
   * @param transformation items transformation
   */
  public void transform(int[] transformation) {
    List<T> transformedItems = new ArrayList<T>(transformation.length);
    for (int i = 0; i < transformation.length; i++) {
      transformedItems.add(ObjectUtil.safeClone(items.get(transformation[i])));
    }
    items = transformedItems;
  }

  @Override
  public Object clone() {
    try {
      @SuppressWarnings("unchecked")
      BaseInput<T> clone = (BaseInput<T>) super.clone();
      clone.items = CollectionUtil.deepCopyOf(items);
      return clone;
    } catch (CloneNotSupportedException e) {
      // this shouldn't happen, since we are Cloneable
      throw new InternalError();
    }
  }
}
