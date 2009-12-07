package com.googlecode.caparf.framework;

public interface Algorithm<I extends AlgorithmInput, O extends AlgorithmOutput> {
  O solve(I input);
}
