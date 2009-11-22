package com.googlecode.caparf.framework;

public interface AlgorithmOutputVerifier<O extends AlgorithmOutput> {
	boolean verify(O output);
}
