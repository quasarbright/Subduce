package language.interpreter;

import language.interpreter.expression.FunctionCallExpression;
import language.interpreter.expression.value.Value;

public class SimpleContext implements Context<FunctionCallExpression, String, Value> {
  private final CallStack<FunctionCallExpression> callStack;
  private final Environment<String, Value> environment;

  public SimpleContext(CallStack<FunctionCallExpression> callStack, Environment<String, Value> environment) {
    this.callStack = callStack;
    this.environment = environment;
  }

  @Override
  public Environment<String, Value> getEnvironment() {
    return environment;
  }

  @Override
  public CallStack<FunctionCallExpression> getCallStack() {
    return callStack;
  }

  @Override
  public Context<FunctionCallExpression, String, Value> withCallStack(CallStack<FunctionCallExpression> callStack) {
    return new SimpleContext(callStack, environment);
  }

  @Override
  public Context<FunctionCallExpression, String, Value> withEnvironment(Environment<String, Value> environment) {
    return new SimpleContext(callStack, environment);
  }
}
