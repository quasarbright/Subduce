import unittest
from lexer import *

class TestExpression(unittest.TestCase):
    def testInt(self):
        code = '123'
        expected = [
            Token('<start file>', 1),
            Token('<number>', 1, 1, 4, 123),
            Token('<end file>', 2)
        ]
        actual = tokenize(code)
        self.assertEqual(actual, expected)
    
    def testNegativeInt(self):
        code = '-123'
        expected = [
            Token('<start file>', 1),
            Token('<number>', 1, 1, 5, -123),
            Token('<end file>', 2)
        ]
        actual = tokenize(code)
        self.assertEqual(actual, expected)
    
    def testFloat(self):
        code = '3.14'
        expected = [
            Token('<start file>', 1),
            Token('<number>', 1, 1, 5, 3.14),
            Token('<end file>', 2)
        ]
        actual = tokenize(code)
        self.assertEqual(actual, expected)
    
    def testNegativeFloat(self):
        code = '-6.28'
        expected = [
            Token('<start file>', 1),
            Token('<number>', 1, 1, 6, -6.28),
            Token('<end file>', 2)
        ]
        actual = tokenize(code)
        self.assertEqual(actual, expected)
    
    def testNegativeStartPoint(self):
        code = '-.28'
        expected = [
            Token('<start file>', 1),
            Token('<number>', 1, 1, 5, -.28),
            Token('<end file>', 2)
        ]
        actual = tokenize(code)
        self.assertEqual(actual, expected)
    
    def testNegativeEndPoint(self):
        code = '-6.'
        expected = [
            Token('<start file>', 1),
            Token('<number>', 1, 1, 4, -6.),
            Token('<end file>', 2)
        ]
        actual = tokenize(code)
        self.assertEqual(actual, expected)
    
    def testString(self):
        code = '"hello"'
        expected = [
            Token('<start file>', 1),
            Token('<string>', 1, 1, 8, "hello"),
            Token('<end file>', 2)
        ]
        self.assertEqual(tokenize(code), expected)
    def testEscapeQuote(self):
        code = r'"quote \" ok"'
        expected = [
            Token('<start file>', 1),
            Token('<string>', 1, 1, len(code)+1, 'quote " ok'),
            Token('<end file>', 2)
        ]
        actual = tokenize(code)
        self.assertEqual(actual, expected)
    
    def testEscapeBackslash(self):
        code = r'"quote \\\" ok"'
        expected = [
            Token('<start file>', 1),
            Token('<string>', 1, 1, len(code)+1, 'quote \\" ok'),
            Token('<end file>', 2)
        ]
        actual = tokenize(code)
        self.assertEqual(actual, expected)
    
    def testEscapeCharacter(self):
        code = r'"\a\n\t\r\b"'
        expected = [
            Token('<start file>', 1),
            Token('<string>', 1, 1, len(code)+1, '\a\n\t\r\b'),
            Token('<end file>', 2)
        ]
        actual = tokenize(code)
        self.assertEqual(actual, expected)
    
    def testBool(self):
        code = 'true'
        expected = [
            Token('<start file>', 1),
            Token('<boolean>', 1, 1, 5, True),
            Token('<end file>', 2)
        ]
        self.assertEqual(tokenize(code), expected)
        code = 'false'
        expected = [
            Token('<start file>', 1),
            Token('<boolean>', 1, 1, 6, False),
            Token('<end file>', 2)
        ]
        self.assertEqual(tokenize(code), expected)
    def testAddition(self):
        code = '(+ 1 24)'
        expected = [
            Token('<start file>', 1),
            Token('<start function>', 1, 1, 2),
            Token('<identifier>', 1, 2, 3, '+'),
            Token('<number>', 1, 4, 5, 1),
            Token('<number>', 1, 6, 8, 24),
            Token('<end function>', 1, 8, 9),
            Token('<end file>', 2)
        ]
        actual = tokenize(code)
        self.assertEqual(actual, expected)

    def testMultiline(self):
        code = '(+\n\t1\n\t2)'
        expected = [
            Token('<start file>', 1),
            Token('<start function>', 1, 1, 2),
            Token('<newline>', 2),
            Token('<number>', 2, 2, 3, 1),
            Token('<newline>', 3),
            Token('<number>', 3, 2, 3, 2),
            Token('<end function>', 3, 3, 4),
            Token('<end file>', 4)
        ]
        self.assertEqual(tokenize(code), expected)
    
    def testOneCharacter(self):
        code = '1'
        expected = [
            Token('<start file>', 1),
            Token('<number>', 1, 1, 2, 1),
            Token('<end file>', 2)
        ]
        actual = tokenize(code)
        self.assertEqual(actual, expected)
    
    def testEmpty(self):
        code = ''
        expected = [
            Token('<start file>', 1),
            Token('<end file>', 2)
        ]
        actual = tokenize(code)
        self.assertEqual(actual, expected)

class TestIndentation(unittest.TestCase):
    pass
    
class TestAssignment(unittest.TestCase):
    def testAssignment(self):
        code = 'var = 2'
        expected = [
            Token('<start file>', 1),
            Token('<identifier>', 1, 1, 4, 'var'),
            Token('<equals>', 1, 5, 6),
            Token('<number>', 1, 7, 8, 2),
            Token('<end file>', 2)
        ]
        self.assertEqual(tokenize(code), expected)

if __name__ == '__main__':
    unittest.main()
