package com.googlecode.caparf.framework;

public interface AlgorithmOutputVerifier<I extends AlgorithmInput, O extends AlgorithmOutput> {
  boolean verify(I input, O output);
}
