package com.googlecode.caparf.framework;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class AlgorithmInputReader<I extends AlgorithmInput> {

  private ArrayList<I> algorithmInputs;
  
  public AlgorithmInputReader() {
    algorithmInputs = new ArrayList<I>();
  }

  public List<I> readAllInputs() {
    return Collections.unmodifiableList(algorithmInputs);
  }

  public AlgorithmInputReader<I> add(I input) {
    algorithmInputs.add(input);
    return this;
  }

  public AlgorithmInputReader<I> addAll(Collection<? extends I> inputs) {
    algorithmInputs.addAll(inputs);
    return this;
  }

  public AlgorithmInputReader<I> addAll(I... inputs) {
    Collections.addAll(algorithmInputs, inputs);
    return this;
  }
}
