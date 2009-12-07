package com.googlecode.caparf.examples;

import com.googlecode.caparf.algorithms.spp2d.NextFit;
import com.googlecode.caparf.framework.AlgorithmInputReader;
import com.googlecode.caparf.framework.AlgorithmRunner;
import com.googlecode.caparf.framework.spp2d.Input;
import com.googlecode.caparf.framework.spp2d.InputReader;
import com.googlecode.caparf.framework.spp2d.OutputVerifier;
import com.googlecode.caparf.tests.spp2d.BerkeyWangGenerator;
import com.googlecode.caparf.tests.spp2d.BerkeyWangGenerator.Type;

public class NextFitTestScenario {

  public static void main(String[] args) {
    NextFit algorithm = new NextFit();
    
    OutputVerifier verifier = new OutputVerifier();
    
    AlgorithmInputReader<Input> inputReader = new InputReader()
        .addAll(BerkeyWangGenerator.getReferenceInstances(Type.CLASS_I))
        .addAll(BerkeyWangGenerator.getReferenceInstances(Type.CLASS_II))
        .addAll(BerkeyWangGenerator.getReferenceInstances(Type.CLASS_III))
        .addAll(BerkeyWangGenerator.getReferenceInstances(Type.CLASS_IV))
        .addAll(BerkeyWangGenerator.getReferenceInstances(Type.CLASS_V))
        .addAll(BerkeyWangGenerator.getReferenceInstances(Type.CLASS_VI));
    
    AlgorithmRunner.run(inputReader, algorithm, verifier);
  }
}
