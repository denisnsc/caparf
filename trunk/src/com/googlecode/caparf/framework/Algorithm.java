package com.googlecode.caparf.framework;

public interface Algorithm<I extends AlgorithmInput, O extends AlgorithmOutput> {
	void solve(I input, O output);
}
