package language.interpreter;

import java.util.Optional;

/**
 * Immutable runtime environment storing variable names and values.
 * (the environment is immutable, not necessarilly the variables).
 * Essentially contains a list of variable scopes (not a tree).
 * Prioritizes the most recently defined version of each variable.
 * To be used as an accumulator in recursive evaluation.
 *
 * @param <ReferenceType> type representing a variable reference.
 * @param <ValueType> type representing evaluated values stored in variables.
 */
public interface Environment<ReferenceType, ValueType> {
  /**
   * Find the value of the given variable name.
   * Uses the most recently defined version.
   *
   * @param name the variable name
   * @return the value of the variable if found
   */
  Optional<ValueType> getValue(ReferenceType name);

  /**
   * Creates a new environment with a new scope and returns it.
   * DOES NOT mutate this environment.
   *
   * @return a copy of this environment with another scope
   */
  Environment<ReferenceType, ValueType> withNewScope();

  /**
   * Creates a new environment with the given variable and its value added to the current scope.
   *
   * @param name variable name
   * @param value variable value
   * @return a copy of this environment with the variable and its value added to the current scope
   */
  Environment<ReferenceType, ValueType> withNewVariable(ReferenceType name, ValueType value);
}
