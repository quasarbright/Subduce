package language.interpreter;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import language.interpreter.builtins.NumberEqualFunction;
import language.interpreter.expression.value.BooleanValue;
import language.interpreter.expression.value.NumberValue;
import language.interpreter.expression.value.StringValue;
import language.interpreter.expression.value.Value;
import language.interpreter.expression.value.listValue.ListValue;

public abstract class ValueRuntimeTest<StatementType, ExpressionType> extends RuntimeTest<StatementType, ExpressionType, Value> {
  protected final String sumRangeDefinition = "def (sum-range min max) { return (if (>= min max) min (+ min (sum-range (+ 1 min) max)))} print (sum-range 0 100)";
  protected final double num = 1;
  protected final double gtEpsilon = NumberEqualFunction.TOLERANCE * 2.0;
  protected final double ltEpsilon = NumberEqualFunction.TOLERANCE / 2.0;

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

  protected Value toListValue(Value... values) {
    return ListValue.fromValues(Arrays.asList(values));
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
    // test equality tolerance edge case
    testEvaluation("(== "+num+" "+(num+ltEpsilon)+")", toValue(true));
    testEvaluation("(== "+num+" "+(num-ltEpsilon)+")", toValue(true));
    testEvaluation("(== "+num+" "+(num+gtEpsilon)+")", toValue(false));
    testEvaluation("(== "+num+" "+(num-gtEpsilon)+")", toValue(false));
  }

  @Test
  public void testNotEquals() {
    testEvaluation("(!= 1 2)", toValue(true));
    testEvaluation("(!= 1 1)", false);
    // test equality tolerance edge case
    testEvaluation("(!= "+num+" "+(num+ltEpsilon)+")", toValue(false));
    testEvaluation("(!= "+num+" "+(num-ltEpsilon)+")", toValue(false));
    testEvaluation("(!= "+num+" "+(num+gtEpsilon)+")", toValue(true));
    testEvaluation("(!= "+num+" "+(num-gtEpsilon)+")", toValue(true));
  }

  @Test
  public void testLessThan() {
    testEvaluation("(< 1 2 3 4)", toValue(true));
    testEvaluation("(< 1 1)", toValue(false));
    // if a == b, a < b should be false even if it's "really" true
    testEvaluation("(< 1 "+num+" "+(num + ltEpsilon)+")", toValue(false));
  }

  @Test
  public void testLists() {
    testEvaluation("empty", toListValue());
    testEvaluation("(cons 0 empty)", toListValue(toValue(0)));
    testEvaluation(
            "(cons 0 (cons 1 (cons 2 (cons 3 empty))))",
            toListValue(
                    toValue(0),
                    toValue(1),
                    toValue(2),
                    toValue(3)
            ));

    testEvaluation(
            "(cons 0 (cons \"hello\" (cons true (cons empty empty))))",
            toListValue(
                    toValue(0),
                    toValue("hello"),
                    toValue(true),
                    toListValue()
            ));

    testPostRunEvaluation(
            makeSource(
                    "two = 2",
                    "a = (cons two empty)",
                    "b = (cons 1 a)",
                    "c = (cons 0 b)"
                    ),
            "c",
            toListValue(
                    toValue(0),
                    toValue(1),
                    toValue(2)
            )
    );

    testEvaluation("(list 0 1 2)", toListValue(toValue(0), toValue(1), toValue(2)));
    testEvaluation(
            "(list 0 \"hello\" true empty)",
            toListValue(
                    toValue(0),
                    toValue("hello"),
                    toValue(true),
                    toListValue()
            ));
  }

  @Test
  public void testFirst() {
    testEvaluation("(first (list 0 1 2))", toValue(0));
    testEvaluation("(first (list 2 1 0))", toValue(2));
    testEvaluation("(first (cons 0 empty)))", toValue(0));
    testEvaluation("(first (cons \"hello\" empty)))", toValue("hello"));
    testEvaluation("(first (cons true empty)))", toValue(true));
    testEvaluation("(first (cons (list 1) empty)))", toListValue(toValue(1)));
  }

  @Test
  public void testRest() {
    testEvaluation("(rest (list 0 1 2))", toListValue(toValue(1), toValue(2)));
    testEvaluation("(rest (list 1 2))", toListValue(toValue(2)));
    testEvaluation("(rest (list (list 1 2) (list true)))", toListValue(toListValue(toValue(true))));
  }

  @Test
  public void testConsHuh() {
    testEvaluation("(cons? (list 0 1 2))", toValue(true));
    testEvaluation("(cons? (list 0))", toValue(true));
    testEvaluation("(cons? empty)", toValue(false));
    testEvaluation("(cons? (list empty)))", toValue(true));
  }

  @Test
  public void testEmptyHuh() {
    testEvaluation("(empty? (list 0 1 2))", toValue(false));
    testEvaluation("(empty? (list 0))", toValue(false));
    testEvaluation("(empty? empty)", toValue(true));
    testEvaluation("(empty? (list empty)))", toValue(false));
  }

  @Test
  public void testAnd() {
    testEvaluation("(and true true)", toValue(true));
    testEvaluation("(and true false)", toValue(false));
    testEvaluation("(and false true)", toValue(false));
    testEvaluation("(and false false)", toValue(false));
    testEvaluation("(and true true true false)", toValue(false));
    testEvaluation("(and false)", toValue(false));
    testEvaluation("(and true)", toValue(true));
    testEvaluation("(and true true true true)", toValue(true));
  }

  @Test
  public void testOr() {
    testEvaluation("(or true true)", toValue(true));
    testEvaluation("(or true false)", toValue(true));
    testEvaluation("(or false true)", toValue(true));
    testEvaluation("(or false false)", toValue(false));
    testEvaluation("(or true true true false)", toValue(true));
    testEvaluation("(or false)", toValue(false));
    testEvaluation("(or true)", toValue(true));
    testEvaluation("(or true true true true)", toValue(true));
  }

  @Test
  public void testNot() {
    testEvaluation("(not true)", toValue(false));
    testEvaluation("(not false)", toValue(true));
  }

  @Test
  public void testMutualRecursion() {
    String source =
            "def (foo a) { return (if (== a 0) 0 (+ a (bar (- a 1)))) }\n" +
            "def (bar a) { return (if (== a 0) 0 (+ a (foo (- a 1)))) }\n"+
            "x = (foo 100)";
    testPostRunEvaluation(source, "x", toValue(5050));
    testPostRunEvaluation(source, "(foo 100)", toValue(5050));
  }

  @Test
  public void testVariableUseFunctionBeforeDefinition() {
    String source =
            "x = (foo 2)\n" +
            "def (foo a) { return (+ 1 a) }\n";
    testPostRunEvaluation(source, "x", toValue(3));
    /// left off here thinking about distinguishing between statements and expressions to avoid
    // all of this confusion
  }

  @Test
  public void testFunctionUsingVariable() {
    String source = makeSource(
            "x = 2",
            "def (foo a) { return (+ x a) }"
    );
    testPostRunEvaluation(source, "(foo 3)", toValue(5.0));
    source = makeSource(
            "x = 2",
            "y = (foo 3)",
            "def (foo a) { return (+ x a) }"
    );
    testPostRunEvaluation(source, "y", toValue(5.0));
    testPostRunEvaluation(source, "(foo 3)", toValue(5.0));
  }

  @Test
  public void testVeryMutualRecursion() {
    String source =
            "x = 1\n" +
            "y = (f1 4)\n" + // should be 10
            "def (f1 a) { return (if (== a 0) a (+ a (f2 (- a x))))}\n" +
            "def (f3 a) { return (f4 a) }\n" +
            "def (f2 a) { return (f3 a) }\n" +
            "def (f4 a) { return (f1 a) }\n";
    testPostRunEvaluation(source, "y", toValue(10));
  }

  @Test
  public void testEmptyProgram() {
    String source = "";
    testPostRunEvaluation(source, "(+ 1 2)", toValue(3));
  }

  @Test
  public void testLazyEvaluation() {
    testEvaluation("(if true 0 (not 123))", toValue(0));
    testPostRunEvaluation(
            makeSource(
                    "def (foo a) { return (foo a) }",
                    "x = (if false (foo 1) 0)"
            ),
            "x",
            toValue(0)
    );
    String infiniteLoop = "def (foo a) { return (foo a) }\n";
    testPostRunEvaluation(infiniteLoop, "(and false (foo a))", toValue(false));
    testPostRunEvaluation(infiniteLoop, "(and true true false (foo a))", toValue(false));
    testPostRunEvaluation(infiniteLoop, "(or true (foo a))", toValue(true));
    testPostRunEvaluation(infiniteLoop, "(or false true false (foo a))", toValue(true));
  }

  @Test
  public void testStruct() {
    String source = makeSource(
            "(define-struct posn (x y))",
            "(define-struct coord (x y))",
            "p1 = (make-posn 0 1)",
            "c1 = (make-coord 0 1)",
            "x = (posn-x p1)",
            "y = (posn-y p1)",
            "def (make-fake-posn x y) {",
            "  (define-struct posn (x y))",
            "  return (make-posn x y)",
            "}",
            "p2 = (make-fake-posn 0 1)"
    );
    testPostRunEvaluation(source, "x", toValue(0));
    testPostRunEvaluation(source, "y", toValue(1));
    testPostRunEvaluation(source, "(posn? p1)", toValue(true));
    testPostRunEvaluation(source, "(posn? x)", toValue(false));
    testPostRunEvaluation(source, "(posn? true)", toValue(false));
    testPostRunEvaluation(source, "(posn? c1)", toValue(false));
    testPostRunEvaluation(source, "(posn? p2)", toValue(false));
  }

  @Test
  public void testUniversalEquals() {
    testEvaluation("(equal? 1 1)", toValue(true));
    testEvaluation("(equal? 1 0)", toValue(false));
    testEvaluation("(equal? 0 1)", toValue(false));
    testEvaluation("(equal? true true)", toValue(true));
    testEvaluation("(equal? false false)", toValue(true));
    testEvaluation("(equal? false true)", toValue(false));
    testEvaluation("(equal? \"f\" \"f\")", toValue(true));
    testEvaluation("(equal? \"f\" \"aaaaf\")", toValue(false));
    testEvaluation("(equal? 1 true)", toValue(false));
    testEvaluation("(equal? 0 false)", toValue(false));
    String source = makeSource(
            "(define-struct posn (x y))",
            "(define-struct coord (x y))",
            "p1 = (make-posn 1 2)",
            "p1* = (make-posn 1 2)",
            "p2 = (make-posn 4 5)",
            "c1 = (make-coord 1 2)",
            "def (make-fake-posn x y) {",
            "  (define-struct posn (x y))",
            "  return (make-posn x y)",
            "}",
            "p3 = (make-fake-posn 1 2)"
            );
    testPostRunEvaluation(source, "(equal? p1 p1)", toValue(true));
    testPostRunEvaluation(source, "(equal? p1 p1*)", toValue(true));
    testPostRunEvaluation(source, "(equal? p1 p2)", toValue(false));
    testPostRunEvaluation(source, "(equal? p1 p2)", toValue(false));
    testPostRunEvaluation(source, "(equal? p1 p3)", toValue(false));
    testPostRunEvaluation(source, "(equal? p1 c1)", toValue(false));
    testPostRunEvaluation(source, "(equal? p1 444)", toValue(false));
    testPostRunEvaluation(source, "(equal? 444 p1)", toValue(false));
    // function equality (weird)
    testEvaluation("(equal? + +)", toValue(true));
    testEvaluation("(equal? + -)", toValue(false));
    testPostRunEvaluation("plus = +", "(equal? plus +)", toValue(true));
    testPostRunEvaluation("def (eq a b) { return (== a b) }", "(equal? eq ==)", toValue(false));
    testPostRunEvaluation("def (eq a b) { return (== a b) }", "(equal? eq eq)", toValue(true));
    testPostRunEvaluation("def (eq a b) { return (== a b) }\neeq = eq", "(equal? eq eeq)", toValue(true));
    testPostRunEvaluation(
            makeSource(
                    "def (foo a) { return a }",
                    "def (bar a) { return a }"
            ),
            "(equal? foo bar)",
            toValue(false)
    );
  }
}
