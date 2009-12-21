package com.googlecode.caparf.examples;

import com.googlecode.caparf.algorithms.spp2d.SimpleFit;
import com.googlecode.caparf.algorithms.spp2d.SimpleFit.ItemOrder;
import com.googlecode.caparf.algorithms.spp2d.SimpleFit.PlacementStrategy;
import com.googlecode.caparf.framework.base.InputSuite;
import com.googlecode.caparf.framework.runner.CaparfCore;
import com.googlecode.caparf.framework.runner.Scenario;
import com.googlecode.caparf.framework.spp2d.Input;
import com.googlecode.caparf.framework.spp2d.Output;
import com.googlecode.caparf.framework.spp2d.OutputVerdict;
import com.googlecode.caparf.framework.spp2d.OutputVerifier;
import com.googlecode.caparf.inputs.spp2d.BerkeyWangGenerator;
import com.googlecode.caparf.inputs.spp2d.BerkeyWangGenerator.Type;

public class SimpleFitTestScenario {

  public static void main(String[] args) {
    // Create scenario instance
    Scenario<Input, Output, OutputVerdict> scenario = new Scenario<Input, Output, OutputVerdict>();    
  
    // Add algorithms to scenario
    scenario.addAlgorithms(
        new SimpleFit(ItemOrder.NEXT_ITEM, PlacementStrategy.DEFAULT),
        new SimpleFit(ItemOrder.NEXT_ITEM, PlacementStrategy.SHIFT_RIGHTMOST_ITEM),
        new SimpleFit(ItemOrder.FIRST_FIT, PlacementStrategy.DEFAULT),
        new SimpleFit(ItemOrder.FIRST_FIT, PlacementStrategy.SHIFT_RIGHTMOST_ITEM));
    
    // Create suite of Berkey and Wang inputs and add it to scenario
    InputSuite<Input> berkeyWangSuite = new InputSuite<Input>()
        .addAll(BerkeyWangGenerator.getReferenceInstances(Type.CLASS_I))
        .addAll(BerkeyWangGenerator.getReferenceInstances(Type.CLASS_II))
        .addAll(BerkeyWangGenerator.getReferenceInstances(Type.CLASS_III))
        .addAll(BerkeyWangGenerator.getReferenceInstances(Type.CLASS_IV))
        .addAll(BerkeyWangGenerator.getReferenceInstances(Type.CLASS_V))
        .addAll(BerkeyWangGenerator.getReferenceInstances(Type.CLASS_VI));
    scenario.addInputSuite(berkeyWangSuite);
    
    // Set output verifier for scenario
    scenario.setVerifier(new OutputVerifier());
    
    // Run scenario
    new CaparfCore<Input, Output, OutputVerdict>().run(scenario);
  }
}
