package com.googlecode.caparf.framework.base;

import com.googlecode.caparf.framework.runner.Scenario;

/**
 * Base class for cutting-and-packing algorithm's input. Each input must have
 * unique (within {@link Scenario}) identifier. No identifier within single
 * {@link Scenario} should be a prefix of another identifier. Input identifier
 * is used for grouping inputs.
 *
 * @author denis.nsc@gmail.com (Denis Nazarov)
 */
public abstract class BaseInput {

  /** Identifier of this input. */
  private final String identifier;

  /**
   * Creates input instance.
   *
   * @param identifier input identifier
   */
  public BaseInput(String identifier) {
    validateIdentifier(identifier);
    this.identifier = identifier;
  }

  private void validateIdentifier(String identifier) {
    if (identifier.isEmpty()) {
      throw new IllegalArgumentException("Identifier must be non-empty");
    }
    if (identifier.startsWith(".")) {
      throw new IllegalArgumentException("Identifier must not start with dot");
    }
    if (identifier.endsWith(".")) {
      throw new IllegalArgumentException("Identifier must not end with dot");
    }
    if (identifier.indexOf("..") != -1) {
      throw new IllegalArgumentException("All parts of identifier must be non-empty");
    }
  }

  /**
   * Returns input identifier which is used for grouping inputs. Identifier
   * consists of several parts separated by dots.
   *
   * @return input identifier
   */
  public final String getIdentifier() {
    return identifier;
  }
}
