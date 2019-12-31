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
  public void testInfiniteRecursion() {
    testRunError(
            makeSource("def (loop a) { return (loop a) }", "(loop 1)"),
            "maximum recursion depth exceeded"
    );
  }

  @Test
  public void testAdd() {
    testEvaluation("(+ 1 2 3 4)", toValue(10));
    testEvaluation("(+ 0.1 1)", toValue(1.1));
    testEvaluation("(+ 0 -1 1 -0.1)", toValue(-0.1));
    testRunError("(+ 1 true)", "+ expected an argument of type [type number] at position 1, got true");
    testRunError("(+ 1 \"hello\")", "+ expected an argument of type [type number] at position 1, got \"hello\"");
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
    testRunError("(== 1 true)", "== expected an argument of type [type number] at position 1, got true");
    testRunError("(== 1)", "== expected 2 arguments, got 1");
    testRunError("(== 1 2 3)", "== expected 2 arguments, got 3");
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
    testRunError("(!= 1 true)", "!= expected an argument of type [type number] at position 1, got true");
    testRunError("(!= 1)", "!= expected 2 arguments, got 1");
    testRunError("(!= 1 2 3)", "!= expected 2 arguments, got 3");
  }

  @Test
  public void testLessThan() {
    testEvaluation("(< 1 2 3 4)", toValue(true));
    testEvaluation("(< 1 1)", toValue(false));
    // if a == b, a < b should be false even if it's "really" true
    testEvaluation("(< 1 "+num+" "+(num + ltEpsilon)+")", toValue(false));
    testRunError("(< 1 true)", "< expected an argument of type [type number] at position 1, got true");
    testRunError("(< 1)", "< expected at least 2 arguments, got 1");
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
    testRunError("(cons 1 2)", "cons expected an argument of type [type list] at position 1, got 2.0");
    testRunError("(cons 1 2 empty)", "cons expected 2 arguments, got 3");
  }

  @Test
  public void testFirst() {
    testEvaluation("(first (list 0 1 2))", toValue(0));
    testEvaluation("(first (list 2 1 0))", toValue(2));
    testEvaluation("(first (cons 0 empty)))", toValue(0));
    testEvaluation("(first (cons \"hello\" empty)))", toValue("hello"));
    testEvaluation("(first (cons true empty)))", toValue(true));
    testEvaluation("(first (cons (list 1) empty)))", toListValue(toValue(1)));
    testRunError("(first 1)", "first expected an argument of type [type list] at position 0, got 1.0");
    testRunError("(first 1 2)", "first expected 1 argument, got 2");
    testRunError("(first empty)", "cannot call first on an empty list");
  }

  @Test
  public void testRest() {
    testEvaluation("(rest (list 0 1 2))", toListValue(toValue(1), toValue(2)));
    testEvaluation("(rest (list 1 2))", toListValue(toValue(2)));
    testEvaluation("(rest (list (list 1 2) (list true)))", toListValue(toListValue(toValue(true))));
    testRunError("(rest 1)", "rest expected an argument of type [type list] at position 0, got 1.0");
    testRunError("(rest 1 2)", "rest expected 1 argument, got 2");
    testRunError("(rest empty)", "cannot call rest on an empty list");
  }

  @Test
  public void testConsHuh() {
    testEvaluation("(cons? (list 0 1 2))", toValue(true));
    testEvaluation("(cons? (list 0))", toValue(true));
    testEvaluation("(cons? empty)", toValue(false));
    testEvaluation("(cons? (list empty)))", toValue(true));
    testRunError("(cons? 1)", "cons? expected an argument of type [type list] at position 0, got 1.0");
    testRunError("(cons? empty empty)", "cons? expected 1 argument, got 2");
  }

  @Test
  public void testEmptyHuh() {
    testEvaluation("(empty? (list 0 1 2))", toValue(false));
    testEvaluation("(empty? (list 0))", toValue(false));
    testEvaluation("(empty? empty)", toValue(true));
    testEvaluation("(empty? (list empty)))", toValue(false));
    testRunError("(empty? 1)", "empty? expected an argument of type [type list] at position 0, got 1.0");
    testRunError("(empty? empty empty)", "empty? expected 1 argument, got 2");
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
    testRunError("(and 1)", "and expected boolean arguments, got 1.0");
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
    testRunError("(or 1)", "or expected boolean arguments, got 1.0");
  }

  @Test
  public void testNot() {
    testEvaluation("(not true)", toValue(false));
    testEvaluation("(not false)", toValue(true));
    testRunError("(not 1)", "not expected an argument of type [type boolean] at position 0, got 1.0");
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
  public void testBadMutualRecursion() {
    testRunError(
            makeSource(
                    "def (foo a) { return (bar a) }",
                    "def (bar a) { return (foo a) }",
                    "x = (foo 1)"
            ),
            "maximum recursion depth exceeded"
    );
  }

  @Test
  public void testVariableUseFunctionBeforeDefinition() {
    String source =
            "x = (foo 2)\n" +
            "def (foo a) { return (+ 1 a) }\n";
    testPostRunEvaluation(source, "x", toValue(3));
  }

  @Test
  public void testFunctionUsingVariable() {
    String source = makeSource(
            "def (foo a) { return (+ x a) }",
            "x = 2"
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
  public void testBadFunctionVariableUsage() {
    testRunError(
            makeSource(
                    "y = (foo 3)",
                    "def (foo a) { return (+ x a) }",
                    "x = 2"
            ),
            "variable x is not defined"
    );
    testRunError(
            makeSource(
                    "x = (foo 2)",
                    "def (foo a) { return x }"
            ),
            "variable x is not defined"
    );
  }

  @Test
  public void testEmptyProgram() {
    String source = "";
    testPostRunEvaluation(source, "(+ 1 2)", toValue(3));
  }

  @Test
  public void testMutationAndScoping() {
    // can't mutate
    testRunError(
            makeSource(
                    "x = 2",
                    "x = 3"),
            "variable x already defined in this scope");
    // can't mutate argument variables
    testRunError(
            makeSource(
                    "def (foo a) {",
                    "  a = 2",
                    " return a",
                    "}",
                    "(foo 12)"
            ),
            "variable a already defined in this scope"
    );


    // functions use the innermost definition of a variable
    String source = makeSource(
            "x = 2",
            "def (foo a) {",
            "  x = 3",
            "  return x",
            "}"
    );
    testPostRunEvaluation(source, "(foo 5)", toValue(3));
    testPostRunEvaluation(source, "x", toValue(2));


    // functions use the innermost definition of a function
    source = makeSource(
            "def (f a) { return 1 }",
            "def (foo a) {",
            "  def (f a) { return 2 }",
            "  return (f 123)",
            "}"
    );
    testPostRunEvaluation(source, "(f 123)", toValue(1));
    testPostRunEvaluation(source, "(foo 123)", toValue(2));


    // functions use the correct version of the variables and don't know about other functions'
    // inner variables
    source = makeSource(
            "x = 2",
            "def (foo a) { return x }",
            "def (bar a) {",
            "  x = 3 ",
            "  return (foo a)",
            "}"
    );
    testPostRunEvaluation(source, "x", toValue(2.0));
    testPostRunEvaluation(source, "(foo 1)", toValue(2.0));
    testPostRunEvaluation(source, "(bar 1)", toValue(2.0));

    // variable assignments happen in chronological order
    source = makeSource(
            "x = 2",
            "def (foo a) {",
            "  b = x",
            "  x = 3",
            "  return b",
            "}"
    );
    testPostRunEvaluation(source, "x", toValue(2));
    testPostRunEvaluation(source, "(foo 123123)", toValue(2));
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

    testRunError(
            makeSource(
                    "(define-struct posn (x y))",
                    "(define-struct posn (x y))"
                    ),
            "variable posn? already defined in this scope"
    );
    testRunError(
            makeSource(
                    "def (posn? p) { return true }",
                    "(define-struct posn (x y))"
                    ),
            "variable posn? already defined in this scope"
    );
    testRunError(
            makeSource(
                    "(define-struct posn (x y))",
                    "def (posn? p) { return true }"
                    ),
            "variable posn? already defined in this scope"
    );
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

    testRunError("(equal? 1)", "equal? expected 2 arguments, got 1");
    testRunError("(equal? 1 2 3)", "equal? expected 2 arguments, got 3");
  }
}
