package language.interpreter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import language.interpreter.expression.Expression;
import language.interpreter.expression.ExpressionVisitor;
import language.interpreter.expression.value.Value;
import language.interpreter.expression.value.functionValue.SubduceFunctionValue;

/**
 * Adds definition to environment if there is any.
 * Stateful.
 */
public class DefinitionEvaluator implements ExpressionVisitor<Environment<String, Value>> {
  private final Environment<String, Value> environment;
  // the variables added in this visitor's lifetime
  private final Map<String, SubduceFunctionValue> addedFunctions;
  private final ExpressionEvaluator evaluator;

  public DefinitionEvaluator(Environment<String, Value> environment, ExpressionEvaluator evaluator) {
    this.environment = environment;
    addedFunctions = new HashMap<>();
    this.evaluator = evaluator;
  }

  @Override
  public Environment<String, Value> visitFunctionCall(Expression function, List<Expression> arguments) {
    return environment;
  }

  @Override
  public Environment<String, Value> visitFunctionDefinition(String name, List<String> argnames, Expression body) {
    SubduceFunctionValue function = new SubduceFunctionValue(argnames, body, environment, name);
    addedFunctions.put(name, function);
    Environment<String, Value> newEnvironment = environment.withNewVariable(name, function);
    // even though sequence will handle this, you still have to do it here in case the program is
    // just one function definition. Then, it won't be a sequence
    function.setEnvironment(newEnvironment);
    return newEnvironment;
  }

  @Override
  public Environment<String, Value> visitLambda(List<String> argnames, Expression body) {
    return environment;
  }

  @Override
  public Environment<String, Value> visitSequence(List<Expression> expressions) {
    Environment<String, Value> accumulatedEnvironment = environment;
    // do function definitions first
    List<Expression> functionDefinitions = justFunctionDefinitions(expressions);
    List<Expression> notFunctionDefinitions = noFunctionDefinitions(expressions);


    // do the function definitions
    for(Expression functionDefinitionExpression: functionDefinitions) {
      DefinitionEvaluator currentDefinitionEvaluator = new DefinitionEvaluator(accumulatedEnvironment, evaluator);
      accumulatedEnvironment = functionDefinitionExpression.accept(currentDefinitionEvaluator);

      // record the functions which were defined in this sequence.
      Map<String, SubduceFunctionValue> currentAddedFunctions = currentDefinitionEvaluator.addedFunctions;
      for(Map.Entry<String, SubduceFunctionValue> functionDefinition: currentAddedFunctions.entrySet()) {
        addedFunctions.put(functionDefinition.getKey(), functionDefinition.getValue());
      }
    }


    // make sure the functions know about each other and themselves so the variables can use
    // them even if they're recursive
    // this allows mutual recursion
    for(SubduceFunctionValue functionValue: addedFunctions.values()) {
      functionValue.setEnvironment(accumulatedEnvironment);
    }

    // do everything else that isn't a function definition
    // now variable assignments can use the functions defined in the same scope
    for(Expression expression: notFunctionDefinitions) {
      DefinitionEvaluator currentDefinitionEvaluator = new DefinitionEvaluator(accumulatedEnvironment, evaluator);
      accumulatedEnvironment = expression.accept(currentDefinitionEvaluator);
    }

    // ok, we added all the definitions. Now add all the variables we just added into the functions'
    // environments if there were any. That way functions can use themselves and each other.
    // this means they can also use non-function variables defined in the same scope, regardless of order
    for(SubduceFunctionValue functionValue: addedFunctions.values()) {
      functionValue.setEnvironment(accumulatedEnvironment);
    }

    return accumulatedEnvironment;
  }

  @Override
  public Environment<String, Value> visitVariableAssignment(String name, Expression expression) {
    Value variableValue = evaluator.evaluate(expression, environment);
    return environment.withNewVariable(name, variableValue);
  }

  @Override
  public Environment<String, Value> visitValue(Value value) {
    return environment;
  }

  @Override
  public Environment<String, Value> visitVariableReference(String name) {
    return environment;
  }

  private boolean isFunctionDefinition(Expression expression) {
    return expression.accept(new ExpressionVisitor<Boolean>() {
      @Override
      public Boolean visitFunctionCall(Expression function, List<Expression> arguments) {
        return false;
      }

      @Override
      public Boolean visitFunctionDefinition(String name, List<String> argnames, Expression body) {
        return true;
      }

      @Override
      public Boolean visitLambda(List<String> argnames, Expression body) {
        return false;
      }

      @Override
      public Boolean visitSequence(List<Expression> expressions) {
        // this means there is a sequence inside of a sequence. This should be impossible
        throw new IllegalStateException("Function definition implementation assumes no nested sequences");
      }

      @Override
      public Boolean visitVariableAssignment(String name, Expression expression) {
        return false;
      }

      @Override
      public Boolean visitValue(Value value) {
        return false;
      }

      @Override
      public Boolean visitVariableReference(String name) {
        return false;
      }
    });
  }

  private List<Expression> justFunctionDefinitions(List<Expression> expressions) {
    return expressions.stream()
            .filter(this::isFunctionDefinition)
            .collect(Collectors.toList());
  }

  private List<Expression> noFunctionDefinitions(List<Expression> expressions) {
    return expressions.stream()
            .filter(e -> !isFunctionDefinition(e))
            .collect(Collectors.toList());
  }
}
