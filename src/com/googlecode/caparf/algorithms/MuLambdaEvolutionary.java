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

package com.googlecode.caparf.algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.googlecode.caparf.framework.base.Algorithm;
import com.googlecode.caparf.framework.base.BaseInput;
import com.googlecode.caparf.framework.base.BaseItem;
import com.googlecode.caparf.framework.base.BaseItemPlacement;
import com.googlecode.caparf.framework.base.BaseOutput;
import com.googlecode.caparf.framework.base.Interruptible;
import com.googlecode.caparf.framework.base.LowerBound;
import com.googlecode.caparf.framework.base.ObjectiveComparator;
import com.googlecode.caparf.framework.util.ObjectUtil;

/**
 * Evolutionary algorithm for all types of cutting-and-packing problems. This
 * algorithm was proposed by Ingo Rechenberg (<a
 * href="http://en.wikipedia.org/wiki/Evolution_strategy">wikipedia</a>). For
 * the good overview of evolutionary algorithms look at article by P. Borisovsky
 * and A. Eremeev <a href="http://dx.doi.org/10.1016/j.tcs.2008.03.008">
 * "Comparing evolutionary algorithms to the (1+1)-EA"</a>.
 * <p>
 * {@code MuLambdaEvolutionary} algorithm is configured by:
 * <ol>
 * <li>Algorithm-decoder that is responsible for decoding chromosomes to problem
 * outputs. Chromosome can be treated as permutation of items in the problem
 * input. Algorithm-decoder will be provided with already permuted input.
 * <li>Lower bound that will be called exactly once in order to get global lower
 * bound value.
 * <li>Selection operation which can be either
 * {@link SelectionOperation#SELECT_FROM_NEW_POPULATION_ONLY} for {@code
 * (μ,λ)-EA} or {@link SelectionOperation#SELECT_FROM_NEW_AND_BEST_POPULATION}
 * for {@code (μ+λ)-EA}.
 * <li>Value of {@code μ} and {@code λ}.
 * </ol>
 * <p>
 * Mutation operation in current implementation is simply {@code 2-SWAP}. One
 * can override {@link #mutate(Chromosome)} in order to change its behavior.
 *
 * @param <I> algorithm input class
 * @param <O> algorithm output class
 *
 * @author denis.nsc@gmail.com (Denis Nazarov)
 */
public class MuLambdaEvolutionary<I extends BaseInput<? extends BaseItem>,
    O extends BaseOutput<? extends BaseItemPlacement>> extends Algorithm<I, O>
    implements Interruptible {

  /** Select operations used for selecting best population. */
  public static enum SelectionOperation {
    /** Best population will be selected based only on new population. */
    SELECT_FROM_NEW_POPULATION_ONLY,
    /** Best population will be selected based on new and best populations. */
    SELECT_FROM_NEW_AND_BEST_POPULATION
  }

  /** Algorithm used to decode chromosomes. */
  private final Algorithm<I, O> decoder;
  /** Global lower bound. */
  private final LowerBound<I> lowerBound;
  /** Selection operation. */
  private final SelectionOperation selectionOperation;
  /** Size of best population stored by algorithm. */
  private final int mu;
  /** Size of new population that will be generated on each algorithm step. */
  private final int lambda;

  /** Whether the algorithm were interrupted. */
  private boolean interrupted;

  /** Comparator for objection function values. */
  private ObjectiveComparator comparator;
  /** Random engine used by algorithm. */
  private Random rnd;
  /** Input to solve by algorithm. */
  private I input;
  /** Best population. */
  private List<Chromosome> population;

  /**
   * Creates {@code MuLambdaEvolutionary} by the given parameters.
   *
   * @param decoder algorithm to use for decoding chromosomes
   * @param lowerBound global lower bound
   * @param selectionOperation operation for selecting best population
   * @param mu size of best population stored by algorithm
   * @param lambda size of new population that will be generated on each
   *          algorithm step
   */
  public MuLambdaEvolutionary(Algorithm<I, O> decoder, LowerBound<I> lowerBound,
      SelectionOperation selectionOperation, int mu, int lambda) {
    this.decoder = decoder;
    this.lowerBound = lowerBound;
    this.selectionOperation = selectionOperation;
    this.mu = mu;
    this.lambda = lambda;
    this.comparator = ObjectiveComparator.getSingleton();
    this.rnd = new Random();
  }

  @Override
  public O solve(I input) {
    interrupted = false;
    this.input = input;
    Number bound = lowerBound.calculateLowerBound(input);

    population = new ArrayList<Chromosome>(mu);
    for (int i = 0; i < mu; i++) {
      population.add(generateRandomChromosome());
    }
    Collections.sort(population);

    while (!interrupted) {
      if (comparator.compare(bound, population.get(0).objectiveValue) == 0) {
        break;
      }

      List<Chromosome> candidates = new ArrayList<Chromosome>(lambda);
      for (int i = 0; i < lambda; i++) {
        int id = rnd.nextInt(population.size());
        candidates.add(mutate(population.get(id)));
      }

      population = selectBestPopulation(candidates, population);
    }

    Chromosome best = population.get(0);
    Integer[] inversedPermutation = new Integer[best.itemsPermutation.size()];
    for (int i = 0; i < best.itemsPermutation.size(); i++) {
      inversedPermutation[best.itemsPermutation.get(i)] = i;
    }
    best.solution.transform(Arrays.asList(inversedPermutation));

    return best.solution;
  }

  @Override
  public void interrupt() {
    interrupted = true;
  }

  /**
   * Selects best population from the given {@code newPopulation} and
   * {@code bestPopulation}. Result depends on {@link #selectionOperation} which
   * can be either {@link SelectionOperation#SELECT_FROM_NEW_POPULATION_ONLY} or
   * {@link SelectionOperation#SELECT_FROM_NEW_AND_BEST_POPULATION}.
   *
   * @param newPopulation newly generated population
   * @param bestPopulation previous best population
   * @return best population
   */
  public List<Chromosome> selectBestPopulation(List<Chromosome> newPopulation,
      List<Chromosome> bestPopulation) {
    switch (selectionOperation) {
      case SELECT_FROM_NEW_POPULATION_ONLY:
        break;
      case SELECT_FROM_NEW_AND_BEST_POPULATION:
        newPopulation.addAll(population);
        break;
    }
    Collections.sort(newPopulation);
    return newPopulation.subList(0, mu);
  }

  /**
   * Mutates the given chromosome {@code original} according to {@code 2-}swap
   * rule. Override this method in order to use different mutation operation.
   *
   * @param original chromosome to mutate
   * @return mutated chromosome
   */
  public Chromosome mutate(Chromosome original) {
    Chromosome result = new Chromosome();
    result.itemsPermutation = new ArrayList<Integer>(original.itemsPermutation);
    for (int i = 0; i < 2; i++) {
      int a = rnd.nextInt(result.itemsPermutation.size());
      int b = rnd.nextInt(result.itemsPermutation.size());
      Integer tmp = result.itemsPermutation.get(a);
      result.itemsPermutation.set(a, result.itemsPermutation.get(b));
      result.itemsPermutation.set(b, tmp);
    }
    result.decode();
    return result;
  }

  @Override
  public String getDisplayName() {
    return "MuLambdaEA(" + decoder.getDisplayName() + ")";
  }

  /**
   * @return randomly generated chromosome
   */
  public Chromosome generateRandomChromosome() {
    Chromosome result = new Chromosome();
    result.itemsPermutation = new ArrayList<Integer>(input.getItemsCount());
    for (int i = 0; i < input.getItemsCount(); i++) {
      result.itemsPermutation.add(i);
    }
    Collections.shuffle(result.itemsPermutation, rnd);
    result.decode();
    return result;
  }

  public class Chromosome implements Comparable<Chromosome> {
    /** Permutation of items identifiers. */
    public List<Integer> itemsPermutation;
    /** Value of objective function for corresponding output. */
    public Number objectiveValue;
    /** Solution (output) produced by external algorithm. */
    public O solution;

    @Override
    public int compareTo(Chromosome o) {
      return ObjectiveComparator.getSingleton().compare(objectiveValue, o.objectiveValue);
    }

    /** Decodes chromosome by running algorithm for transformed input. */
    public void decode() {
      I transformedInput = ObjectUtil.safeClone(input);
      transformedInput.transform(itemsPermutation);
      solution = decoder.solve(transformedInput);
      objectiveValue = solution.calculateObjectiveFunction();
    }
  }
}
