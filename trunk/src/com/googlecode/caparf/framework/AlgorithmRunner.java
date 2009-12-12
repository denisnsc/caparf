package com.googlecode.caparf.framework;

import com.googlecode.caparf.framework.AlgorithmOutputVerdict.Verdict;

public class AlgorithmRunner {

  public static <I extends AlgorithmInput, O extends AlgorithmOutput,
      V extends AlgorithmOutputVerdict> void run(
          AlgorithmInputReader<I> inputReader, Algorithm<I, O> algorithm,
          AlgorithmOutputVerifier<I, O, V> verifier) {
    int totalInputs = 0, totalOks = 0;
    for (I input : inputReader.readAllInputs()) {
      totalInputs++;
      O output = algorithm.solve(input);
      V verdict = verifier.verify(input, output); 
      if (verdict.getVerdict() == Verdict.CORRECT_ANSWER) {
        totalOks++;
      }
      System.out.println(input + "\n  " + verdict);
    }
    
    if (totalInputs == totalOks) {
      System.out.println("OK: all " + totalInputs + " inputs were solved");
    } else {
      System.out.println("ERROR: " + (totalInputs - totalOks) + " inputs of total " +
          totalInputs + " inputs were not solved");
    }
  }
}
