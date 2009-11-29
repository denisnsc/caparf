package com.googlecode.caparf.framework.spp2d;

/**
 * Base interface of algorithm for solving 2 Dimensional Strip Packing Problem.
 *
 * @param <I> algorithm input
 * @param <O> algorithm output
 *
 * @author denis.nsc@gmail.com (Denis Nazarov)
 */
public interface Algorithm<I extends Input, O extends Output> extends
    com.googlecode.caparf.framework.Algorithm<I, O> {
}
