package language.interpreter;

import org.junit.Before;
import org.junit.Test;

import language.interpreter.expression.value.BooleanValue;
import language.interpreter.expression.value.NumberValue;
import language.interpreter.expression.value.StringValue;
import language.interpreter.expression.value.Value;

public abstract class ValueRuntimeTest<StatementType, ExpressionType> extends RuntimeTest<StatementType, ExpressionType, Value> {
  protected String sumRangeDefinition = "def (sum-range min max) { return (if (>= min max) min (+ min (sum-range (+ 1 min) max)))} print (sum-range 0 100)";
//  @Before
//  @Override
//  public void setup() {
//    super.setup();
//
//  }

  @Test
  public void testAtomic() {
    testEvaluation("1", new NumberValue(1));
    testEvaluation("-1", new NumberValue(-1));
    testEvaluation("1.0", new NumberValue(1.0));
    testEvaluation("-1.0", new NumberValue(-1.0));
    testEvaluation("true", new BooleanValue(true));
    testEvaluation("false", new BooleanValue(false));
    testEvaluation("\"hello\"", new StringValue("hello"));
  }

  @Test
  public void testStringEscapes() {
    testEvaluation("\"\\\"string\\\" \\nnewline \\rreturn \\ttab\"", new StringValue("\"string\" \nnewline \rreturn \ttab"));
  }

  @Test
  public void testRecursion() {
    testPostRunEvaluation(sumRangeDefinition, "(sum-range 0 100)", new NumberValue(5050));
  }

  @Test
  public void testBuiltins() {
    /// left off here
  }
}
