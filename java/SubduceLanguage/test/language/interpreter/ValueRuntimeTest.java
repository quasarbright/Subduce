package language.interpreter;

import org.junit.Before;
import org.junit.Test;

import language.interpreter.builtins.NumberEqualFunction;
import language.interpreter.expression.value.BooleanValue;
import language.interpreter.expression.value.NumberValue;
import language.interpreter.expression.value.StringValue;
import language.interpreter.expression.value.Value;

public abstract class ValueRuntimeTest<StatementType, ExpressionType> extends RuntimeTest<StatementType, ExpressionType, Value> {
  protected final String sumRangeDefinition = "def (sum-range min max) { return (if (>= min max) min (+ min (sum-range (+ 1 min) max)))} print (sum-range 0 100)";
  protected final double num = 1;
  protected final double gtEpsilon = NumberEqualFunction.TOLERANCE * 2.0;
  protected final double ltEpsilon = NumberEqualFunction.TOLERANCE / 2.0;
//  @Before
//  @Override
//  public void setup() {
//    super.setup();
//
//  }

  protected Value toValue(String s) {
    return new StringValue(s);
  }

  protected Value toValue(int n) {
    return new NumberValue(n);
  }

  protected Value toValue(double d) {
    return new NumberValue(d);
  }

  protected Value toValue(boolean b) {
    return new BooleanValue(b);
  }

  private void testEvaluation(String expression, String val) {
    testEvaluation(expression, new StringValue(val));
  }

  private void testEvaluation(String expression, int val) {
    testEvaluation(expression, new NumberValue(val));
  }

  private void testEvaluation(String expression, double val) {
    testEvaluation(expression, new NumberValue(val));
  }

  private void testEvaluation(String expression, boolean val) {
    testEvaluation(expression, new BooleanValue(val));
  }


  @Test
  public void testAtomic() {
    testEvaluation("1", 1);
    testEvaluation("-1", -1);
    testEvaluation("1.0", 1.0);
    testEvaluation("-1.0", -1.0);
    testEvaluation("true", true);
    testEvaluation("false", false);
    testEvaluation("\"hello\"", "hello");
  }

  @Test
  public void testStringEscapes() {
    testEvaluation("\"\\\"string\\\" \\nnewline \\rreturn \\ttab\"", "\"string\" \nnewline \rreturn \ttab");
  }

  @Test
  public void testRecursion() {
    testPostRunEvaluation(sumRangeDefinition, "(sum-range 0 100)", toValue(5050));
  }

  @Test
  public void testAdd() {
    testEvaluation("(+ 1 2 3 4)", toValue(10));
    testEvaluation("(+ 0.1 1)", toValue(1.1));
    testEvaluation("(+ 0 -1 1 -0.1)", toValue(-0.1));
  }

  @Test
  public void testEquals() {
    testEvaluation("(== 1 2)", toValue(false));
    testEvaluation("(== 1 1)", true);
    testEvaluation("(== 1 1 1 1 1 1 1 1 1 1 1)", toValue(true));
    testEvaluation("(== 1 1 1 1 1 1 2 1 1 1 1)", toValue(false));
    testEvaluation("(== 1 1 2 2)", toValue(false));
    // test equality tolerance edge case
    testEvaluation("(== "+num+" "+(num+ltEpsilon)+")", toValue(true));
    testEvaluation("(== "+num+" "+(num+gtEpsilon)+")", toValue(false));

  }

  @Test
  public void testLessThan() {
    testEvaluation("(< 1 2 3 4)", toValue(true));
    testEvaluation("(< 1 1)", toValue(false));
    // if a == b, a < b should be false even if it's "really" true
    testEvaluation("(< 1 "+num+" "+(num + ltEpsilon)+")", toValue(false));
  }
}
