package com.googlecode.caparf.framework;

public interface AlgorithmOutputVerifier<I extends AlgorithmInput, O extends AlgorithmOutput,
    V extends AlgorithmOutputVerdict> {
  V verify(I input, O output);
}
