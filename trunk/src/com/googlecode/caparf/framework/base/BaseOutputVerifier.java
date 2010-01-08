package com.googlecode.caparf.framework.base;

/**
 * Base interface for verifier of cutting-and-packing algorithms' outputs.
 *
 * @param <I> algorithm input
 * @param <O> algorithm output
 *
 * @author denis.nsc@gmail.com (Denis Nazarov)
 */
public interface BaseOutputVerifier<I extends BaseInput<? extends BaseItem>, O extends BaseOutput> {

  /**
   * Verifies that the given {@code output} is valid.
   *
   * @param input algorithm input
   * @param output algorithm output
   * @return output verification verdict
   */
  Verdict verify(I input, O output);
}
