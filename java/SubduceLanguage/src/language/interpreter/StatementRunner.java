package language.interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import language.interpreter.expression.Expression;
import language.interpreter.expression.value.Value;
import language.interpreter.expression.value.functionValue.FunctionValue;
import language.interpreter.expression.value.functionValue.JavaFunctionValue;
import language.interpreter.expression.value.functionValue.SubduceFunctionValue;
import language.interpreter.typing.StructType;
import language.interpreter.statement.BaseStatementVisitor;
import language.interpreter.statement.FunctionDefinitionStatement;
import language.interpreter.statement.Statement;
import language.interpreter.statement.StatementVisitor;

/**
 * Adds definition to environment if there is any.
 * Stateful.
 */
public class StatementRunner implements StatementVisitor<Environment<String, Value>> {
  private final Environment<String, Value> environment;
  // the variables added in this visitor's lifetime
  private final ExpressionEvaluator evaluator;

  public StatementRunner(Environment<String, Value> environment, ExpressionEvaluator evaluator) {
    this.environment = environment;
    this.evaluator = evaluator;
  }

  private List<FunctionDefinitionStatement> justFunctionDefinitions(List<Statement> statements) {
    return statements.stream()
            .map((Statement statement) -> {
              return statement.accept(new BaseStatementVisitor<Optional<FunctionDefinitionStatement>>(Optional.empty()) {
                @Override
                public Optional<FunctionDefinitionStatement> visitFunctionDefinition(String name, List<String> argnames, Statement body) {
                  return Optional.of(new FunctionDefinitionStatement(name, argnames, body));
                }
              });
            })
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
  }

  private List<Statement> noFunctionDefinitions(List<Statement> statements) {
    return statements.stream()
            .filter((Statement statement) -> {
              return statement.accept(new BaseStatementVisitor<Boolean>(true) {
                @Override
                public Boolean visitFunctionDefinition(String name, List<String> argnames, Statement body) {
                  return false;
                }
              });
            })
            .collect(Collectors.toList());
  }

  @Override
  public Environment<String, Value> visit(Statement statement) {
    return statement.accept(this);
  }

  @Override
  public Environment<String, Value> visitExpression(Expression expression) {
    // for interactive, don't run this visitor. Instead, capture the statement, evaluate it, and
    // print the value
    evaluator.evaluate(expression);
    return environment;
  }

  @Override
  public Environment<String, Value> visitPrint(Expression expression) {
    // TODO appendable or listnener
    System.out.println(evaluator.evaluate(expression));
    return environment;
  }

  @Override
  public Environment<String, Value> visitReturn(Expression expression) {
    // function application will evaluate this, don't bother
    return environment;
  }

  @Override
  public Environment<String, Value> visitFunctionDefinition(String name, List<String> argnames, Statement body) {
    // not called from sequence!!!
    SubduceFunctionValue functionValue = makeFunction(name, argnames, body);
    Environment<String, Value> newEnvironment =  environment.withNewVariable(name, functionValue);
    functionValue.setEnvironment(newEnvironment);
    return newEnvironment;
  }

  @Override
  public Environment<String, Value> visitStructDefinition(String name, List<String> fieldNames) {
    StructType structType = new StructType(name, fieldNames);
    List<JavaFunctionValue> structFunctions = structType.getStructFunctions();
    Environment<String, Value> newEnvironment = environment;
    for(JavaFunctionValue functionValue: structFunctions) {
      newEnvironment = newEnvironment.withNewVariable(functionValue.getName(), functionValue);
    }
    return newEnvironment;
  }

  private SubduceFunctionValue makeFunction(String name, List<String> argnames, Statement body) {
    return new SubduceFunctionValue.SubduceFunctionBuilder(argnames, body, environment)
            .setName(name)
            .get();
  }

  private SubduceFunctionValue makeFunction(FunctionDefinitionStatement functionDefinitionStatement) {
    return functionDefinitionStatement.accept(new BaseStatementVisitor<>(new IllegalStateException("expected a function definition")) {
      @Override
      public SubduceFunctionValue visitFunctionDefinition(String name, List<String> argnames, Statement body) {
        return makeFunction(name, argnames, body);
      }
    });
  }

  @Override
  public Environment<String, Value> visitVariableDefinition(String name, Expression expression) {
    Value value = evaluator.evaluate(expression);
    return environment.withNewVariable(name, value);
  }

  @Override
  public Environment<String, Value> visitSequence(List<Statement> statements) {
    /*
    algorithm
    1. Define all functions in order
    2. add all functions to all functions' environments so they can all use each other (and themselves)
    3. for each variable definition (in order)
      3.1 define variable
      3.2 add variable to each function's environment

    This will run:
    ```
    def (foo a) { return (bar a) }
    def (bar a) { return x }
    x = 2
    y = (foo 0) //  2.0
    ```
    This will crash:
    def (foo a) { return (bar a) }
    def (bar a) { return x }
    y = (foo 0) // ERROR in bar: x undefined
    x = 2
    ```
    def (foo a)
     */

    Environment<String, Value> accumulatedEnvironment = environment;

    List<FunctionDefinitionStatement> functionDefinitionStatements = justFunctionDefinitions(statements);
    List<Statement> notFunctionDefinitions = noFunctionDefinitions(statements);
    if(functionDefinitionStatements.size() + notFunctionDefinitions.size() != statements.size()) {
      throw new IllegalStateException("statements were not conserved in splitting function-definitions and non-function-definitions");
    }

    // run function definitions and keep track of their values
    List<SubduceFunctionValue> functionValues = new ArrayList<>();
    for(FunctionDefinitionStatement statement: functionDefinitionStatements) {
      SubduceFunctionValue functionValue = makeFunction(statement);
      accumulatedEnvironment = accumulatedEnvironment.withNewVariable(functionValue.getName(), functionValue);
      functionValues.add(functionValue);
    }

    Consumer<Environment<String, Value>> updateFunctionEnvironments = (Environment<String, Value> env) -> {
      // update function environments to include all definitions in the given environment
      for (SubduceFunctionValue functionValue : functionValues) {
        functionValue.setEnvironment(env);
      }
    };
    updateFunctionEnvironments.accept(accumulatedEnvironment);

    // now define variables in order, adding them each to all the functions' environments
    for(Statement statement: notFunctionDefinitions) {
      accumulatedEnvironment = statement.accept(new StatementRunner(accumulatedEnvironment, new ExpressionEvaluator(accumulatedEnvironment)));
      updateFunctionEnvironments.accept(accumulatedEnvironment);
    }

    return accumulatedEnvironment;
  }
}
