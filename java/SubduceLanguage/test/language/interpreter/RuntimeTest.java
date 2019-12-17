package language.interpreter;

import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.util.function.Function;


import static org.junit.Assert.*;

public abstract class RuntimeTest<StatementType, ExpressionType, ValueType> {
  protected Runtime<StatementType, ExpressionType, ValueType> runtime;

  protected abstract Runtime<StatementType, ExpressionType, ValueType> factory();


  @Before
  public void setup() {
    runtime = factory();
  }

  /**
   * Run the program and test what the expression evaluates to in the runtime's context.
   *
   * @param source the code to run
   * @param expression the expression to evaluate
   * @param expected the expected value of the expression
   */
  protected void testPostRunEvaluation(String source, String expression, ValueType expected) {
    setup();
    runtime.run(source);
    testEvaluation(runtime, expression, expected);
  }

  /**
   * Test what the expression evaluates to in a fresh runtime.
   *
   * @param expression the expression to evaluate
   * @param expected the expected value of the expression
   */
  protected void testEvaluation(String expression, ValueType expected) {
    setup();
    testEvaluation(runtime, expression, expected);
  }

  /**
   * Test what the expression evaluates to in the runtime's context.
   *
   * @param runtime the runtime to evaluate the expression against
   * @param expression the expression to evaluate
   * @param expected the expected value of the expression
   */
  protected void testEvaluation(Runtime<StatementType, ExpressionType, ValueType> runtime, String expression, ValueType expected) {
    ValueType actual = runtime.evaluate(expression);
    assertEquals(expected, actual);
  }
}