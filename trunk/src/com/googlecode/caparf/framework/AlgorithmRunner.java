package com.googlecode.caparf.framework;

public class AlgorithmRunner {

  public static <I extends AlgorithmInput, O extends AlgorithmOutput> void run(
      AlgorithmInputReader<I> inputReader, Algorithm<I, O> algorithm,
      AlgorithmOutputVerifier<I, O> verifier) {
    int totalInputs = 0, totalOks = 0;
    for (I input : inputReader.readAllInputs()) {
      totalInputs++;
      O output = algorithm.solve(input);
      if (verifier.verify(input, output)) {
        totalOks++;
      } else {
        System.out.println("Input: " + input);        
        System.out.println("Resulting output: " + output);        
        System.out.println("Output is incorrect");
      }
    }
    
    if (totalInputs == totalOks) {
      System.out.println("OK: all " + totalInputs + " inputs were solved");
    } else {
      System.out.println("ERROR: " + (totalInputs - totalOks) + " inputs of total " +
          totalInputs + " inputs were not solved");
    }
  }
}
