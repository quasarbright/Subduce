package language.interpreter;

import java.util.ArrayList;
import java.util.List;

import language.interpreter.expression.FunctionCallExpression;

public class SimpleCallStack implements CallStack<FunctionCallExpression> {
  private final List<FunctionCallExpression> functionCallExpressions;

  public SimpleCallStack() {
    this(new ArrayList<>());
  }

  public SimpleCallStack(List<FunctionCallExpression> functionCallExpressions) {
    this.functionCallExpressions = new ArrayList<>(functionCallExpressions);
  }

  @Override
  public CallStack<FunctionCallExpression> withNewCall(FunctionCallExpression call) {
    List<FunctionCallExpression> copy = new ArrayList<>(functionCallExpressions);
    copy.add(call);
    return new SimpleCallStack(copy);
  }

  @Override
  public List<FunctionCallExpression> getCalls() {
    return new ArrayList<>(functionCallExpressions);
  }

  @Override
  public String toString() {
    return functionCallExpressions.toString();
  }

  @Override
  public int hashCode() {
    return functionCallExpressions.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if(this == obj) {
      return true;
    }
    if(obj == null || getClass() != obj.getClass()) {
      return false;
    }
    SimpleCallStack other = (SimpleCallStack) obj;
    return functionCallExpressions.equals(other.functionCallExpressions);
  }
}
