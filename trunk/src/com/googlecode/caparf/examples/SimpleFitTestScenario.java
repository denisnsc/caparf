package com.googlecode.caparf.examples;

import com.googlecode.caparf.algorithms.spp2d.SimpleFit;
import com.googlecode.caparf.algorithms.spp2d.SimpleFit.ItemOrder;
import com.googlecode.caparf.algorithms.spp2d.SimpleFit.PlacementStrategy;
import com.googlecode.caparf.framework.AlgorithmInputReader;
import com.googlecode.caparf.framework.AlgorithmRunner;
import com.googlecode.caparf.framework.spp2d.Input;
import com.googlecode.caparf.framework.spp2d.InputReader;
import com.googlecode.caparf.framework.spp2d.OutputVerifier;
import com.googlecode.caparf.tests.spp2d.BerkeyWangGenerator;
import com.googlecode.caparf.tests.spp2d.BerkeyWangGenerator.Type;

public class SimpleFitTestScenario {

  public static void main(String[] args) {
    SimpleFit nextFit = new SimpleFit(ItemOrder.NEXT_ITEM, PlacementStrategy.DEFAULT);
    SimpleFit greedyNextFit = new SimpleFit(ItemOrder.NEXT_ITEM,
        PlacementStrategy.SHIFT_RIGHTMOST_ITEM);
    SimpleFit firstFit = new SimpleFit(ItemOrder.FIRST_FIT, PlacementStrategy.DEFAULT);
    SimpleFit greedyFirstFit = new SimpleFit(ItemOrder.FIRST_FIT,
        PlacementStrategy.SHIFT_RIGHTMOST_ITEM);

    OutputVerifier verifier = new OutputVerifier();

    AlgorithmInputReader<Input> inputReader = new InputReader()
        .addAll(BerkeyWangGenerator.getReferenceInstances(Type.CLASS_I))
        .addAll(BerkeyWangGenerator.getReferenceInstances(Type.CLASS_II))
        .addAll(BerkeyWangGenerator.getReferenceInstances(Type.CLASS_III))
        .addAll(BerkeyWangGenerator.getReferenceInstances(Type.CLASS_IV))
        .addAll(BerkeyWangGenerator.getReferenceInstances(Type.CLASS_V))
        .addAll(BerkeyWangGenerator.getReferenceInstances(Type.CLASS_VI));

    AlgorithmRunner.run(inputReader, nextFit, verifier);
    AlgorithmRunner.run(inputReader, greedyNextFit, verifier);
    AlgorithmRunner.run(inputReader, firstFit, verifier);
    AlgorithmRunner.run(inputReader, greedyFirstFit, verifier);
  }
}
