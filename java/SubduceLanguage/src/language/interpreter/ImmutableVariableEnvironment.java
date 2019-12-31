package language.interpreter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import language.interpreter.expression.value.Value;

/**
 * A variable environment where the value of a variable cannot be changed within its scope.
 * However, a variable can be overridden in a nested scope.
 */
public class ImmutableVariableEnvironment implements Environment<String, Value> {
  // since there should only be one reference of a variable per scope, we can use a map
  // the most recent scope will be the last one in the list
  private final List<Map<String, Value>> scopes;

  public ImmutableVariableEnvironment() {
    this(new ArrayList<>(Collections.singleton(new HashMap<>())));
  }

  private ImmutableVariableEnvironment(List<Map<String, Value>> scopes) {
    this.scopes = scopes;
  }

  @Override
  public Optional<Value> getValue(String name) {
    // loop from last to first since most recent is last
    for(int i = scopes.size() -1; i >= 0; i--) {
      Map<String, Value> scope = scopes.get(i);
      if(scope.containsKey(name)) {
        return Optional.of(scope.get(name));
      }
    }
    return Optional.empty();
  }

  @Override
  public Environment<String, Value> withNewScope() {
    // NOTE: not a deep copy. Copies list, not scope maps
    // modifying maps will modify our maps
    List<Map<String, Value>> scopesCopy = new ArrayList<>(scopes);
    scopesCopy.add(new HashMap<>());
    return new ImmutableVariableEnvironment(scopesCopy);
  }

  @Override
  public Environment<String, Value> withNewVariable(String name, Value value) {
    Map<String, Value> currentScopeCopy = new HashMap<>(scopes.get(scopes.size()-1));
    // prevent variable reassignment within scope
    if(currentScopeCopy.containsKey(name)) {
      // the name is already defined in the current scope. Error out.
      throw new SubduceError("variable "+name+" already defined in this scope");
    }

    // the assignment is valid. do it.
    currentScopeCopy.put(name, value);
    List<Map<String, Value>> scopesCopy = new ArrayList<>(scopes);
    // replace the last scope with the modified copy
    scopesCopy.set(scopesCopy.size()-1, currentScopeCopy);
    return new ImmutableVariableEnvironment(scopesCopy);
  }

  @Override
  public int hashCode() {
    return scopes.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if(this == obj) {
      return true;
    }
    if(obj == null || getClass() != obj.getClass()) {
      return false;
    }
    ImmutableVariableEnvironment other = (ImmutableVariableEnvironment) obj;
    return scopes.equals(other.scopes);
  }

  @Override
  public String toString() {
    return scopes.toString();
  }
}
