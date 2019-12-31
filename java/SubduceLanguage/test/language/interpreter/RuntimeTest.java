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
   * Ensure that running the given code results in an error with the given message.
   *
   * @param source the code to run
   * @param message the error message it should produce
   */
  protected void testRunError(String source, String message) {
    setup();
    try {
      runtime.run(source);
      fail("Didn't throw an error");
    } catch (SubduceError e) {
      assertEquals(message, e.getMessage());
    }
  }

  /**
   * Ensure that running the given code results in the given error.
   *
   * @param source the code to run
   * @param error the error it should produce
   */
  protected void testRunError(String source, SubduceError error) {
    setup();
    try {
      runtime.evaluate(source);
      fail("Didn't throw an error");
    } catch (SubduceError e) {
      assertEquals(error, e);
    }
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

  protected String makeSource(String... lines) {
    StringBuilder ans = new StringBuilder();
    for(String line: lines) {
      ans.append(line).append('\n');
    }
    return ans.toString();
  }
}