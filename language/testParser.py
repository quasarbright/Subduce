import unittest
from lexer import *
from parse import *
from parseTree import *

identifier = Token(lexer.IDENTIFIER, 1, 1, 2, 'var')
lam = Token(lexer.IDENTIFIER, 1, 1, 2, 'lam')
number = Token(lexer.NUMBER, 1, 1, 2, 123)
equals = Token(lexer.EQUALS, 1, 1, 2)
string = Token(lexer.STRING, 1, 1, 2, 'hello')
boolean = Token(lexer.BOOLEAN, 1, 1, 2, True)
startList = Token(START_LIST, 1, 1, 2)
endList = Token(END_LIST, 1, 1, 2)
startFunction = Token(START_FUNCTION, 1, 1, 2)
endFunction = Token(END_FUNCTION, 1, 1, 2)

class TestAtoms(unittest.TestCase):
    def testNumber(self):
        stream = TokenStream([number])
        expected = Number(number)
        actual = parseAtom(stream)
        self.assertEqual(actual, expected)
    def testString(self):
        stream = TokenStream([string])
        expected = String(string)
        actual = parseAtom(stream)
        self.assertEqual(actual, expected)
    def testBoolean(self):
        stream = TokenStream([boolean])
        expected = Boolean(boolean)
        actual = parseAtom(stream)
        self.assertEqual(actual, expected)

class TestParseList(unittest.TestCase):
    def testEmpty(self):
        stream = TokenStream([startList, endList])
        expected = ListExpression([])
        actual = parseList(stream)
        self.assertEqual(actual, expected)
    def testOne(self):
        stream = TokenStream([startList, number, endList])
        expected = ListExpression([Number(number)])
        actual = parseList(stream)
        self.assertEqual(actual, expected)
    def testMany(self):
        stream = TokenStream([startList, number, number, string, endList])
        expected = ListExpression([Number(number), Number(number), String(string)])
        actual = parseList(stream)
        self.assertEqual(actual, expected)
    def testComplex(self):
        # ["hello" (var 123 123) True]
        stream = TokenStream([startList, string, startFunction, identifier, number, number, endFunction, boolean, endList])
        expected = ListExpression([String(string), FunctionCall(identifier, [Number(number), Number(number)]), Boolean(boolean)])
        actual = parseList(stream)
        self.assertEqual(actual, expected)

class TestExpressions(unittest.TestCase):
    def testFunctionCall(self):
        stream = TokenStream([startFunction, identifier, number, number, endFunction])
        expected = FunctionCall(identifier, [Number(number), Number(number)])
        actual = parseFunctionCall(stream)
        self.assertEqual(actual, expected)
    def testLambdaCall(self):
        # ((lam (var) 123) "hello")
        stream = TokenStream([
            startFunction,
            startFunction,
            lam,
            startFunction,
            identifier,
            endFunction,
            number,
            endFunction,
            string,
            endFunction
        ])
        expected = FunctionCall(LambdaDefinition([VariableReference(identifier)], Number(number)), String(string))
        actual = parseFunctionCall(stream)
        self.assertEqual(actual, expected)
    def testLambda(self):
        # (lam (var var) 123)
        stream = TokenStream([startFunction, lam, startFunction, identifier, identifier, endFunction, number, endFunction])
        expected = LambdaDefinition([VariableReference(identifier), VariableReference(identifier)], Number(number))
        # use parseFunctionCall because parseLambda is only supposed to be called from in there
        actual = parseFunctionCall(stream)
        str(actual)
        self.assertEqual(actual, expected)


class TestAssignment(unittest.TestCase):
    def testNumberAssignment(self):
        stream = TokenStream([
            identifier,
            equals,
            number
        ])
        expected = Assignment(identifier, Number(number))
        actual = parseAssignment(stream)
        self.assertEqual(actual, expected)
    def testStringAssignment(self):
        stream = TokenStream([
            identifier,
            equals,
            string
        ])
        expected = Assignment(identifier, String(string))
        actual = parseAssignment(stream)
        self.assertEqual(actual, expected)
    def testBooleanAssignment(self):
        stream = TokenStream([
            identifier,
            equals,
            boolean
        ])
        expected = Assignment(identifier, Boolean(boolean))
        actual = parseAssignment(stream)
        self.assertEqual(actual, expected)
    def testListAssignment(self):
        stream = TokenStream([
            identifier,
            equals,
            startList,
            number,
            number,
            endList
        ])
        expected = Assignment(identifier, ListExpression([Number(number), Number(number)]))
        actual = parseAssignment(stream)
        self.assertEqual(actual, expected)
    def testCallAssignment(self):
        stream = TokenStream([
            identifier,
            equals,
            startFunction,
            identifier,
            number,
            endFunction
        ])
        expected = Assignment(identifier, FunctionCall(identifier, [Number(number)]))
        actual = parseAssignment(stream)
        self.assertEqual(actual, expected)

if __name__ == '__main__':
    unittest.main()
