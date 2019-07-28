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
            Token('<identifier>', 1, 2, 3, '+'),
            Token('<newline>', 2),
            Token('<indent>', 2),
            Token('<number>', 2, 2, 3, 1),
            Token('<newline>', 3),
            Token('<number>', 3, 2, 3, 2),
            Token('<end function>', 3, 3, 4),
            Token('<end file>', 4)
        ]
        actual = tokenize(code)
        self.assertEqual(actual, expected)
    
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
    def testFirstLineIndented(self):
        code = '\th'
        expected = [
            Token('<start file>', 1),
            Token('<indent>', 1),
            Token('<identifier>', 1, 2, 3, 'h'),
            Token('<end file>', 2)
        ]
        actual = tokenize(code)
        self.assertEqual(actual, expected)
    
    def testManyLevels(self):
        code = '0\n\t1\n\t1\n\t\t2\n\t1\n\t\t\t3\n\t1\n0\n\t1'
        '''
        0
            1
            1
                2
            1
                    3
            1
        0
            1
        '''
        expected = [
            Token('<start file>', 1),
            Token('<number>', 1, 1, 2, 0),
            
            Token('<newline>', 2),
            Token('<indent>', 2),
            Token('<number>', 2, 2, 3, 1),
            
            Token('<newline>', 3),
            Token('<number>', 3, 2, 3, 1),
            
            Token('<newline>', 4),
            Token('<indent>', 4),
            Token('<number>', 4, 3, 4, 2),

            Token('<newline>', 5),
            Token('<unindent>', 5),
            Token('<number>', 5, 2, 3, 1),

            Token('<newline>', 6),
            Token('<indent>', 6),
            Token('<indent>', 6),
            Token('<number>', 6, 4, 5, 3),

            Token('<newline>', 7),
            Token('<unindent>', 7),
            Token('<unindent>', 7),
            Token('<number>', 7, 2, 3, 1),

            Token('<newline>', 8),
            Token('<unindent>', 8),
            Token('<number>', 8, 1, 2, 0),

            Token('<newline>', 9),
            Token('<indent>', 9),
            Token('<number>', 9, 2, 3, 1),
            
            Token('<end file>', 10)
        ]
        actual = tokenize(code)
        self.assertEqual(actual, expected)
    
    def testIgnoreCommentIndent(self):
        code = '0\n\t#1\n0'
        expected = [
            Token('<start file>', 1),
            Token('<number>', 1, 1, 2, 0),
            Token('<newline>', 2),
            Token('<newline>', 3),
            Token('<number>', 3, 1, 2, 0),
            Token('<end file>', 4)
        ]
        actual = tokenize(code)
        self.assertEqual(actual, expected)
    
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

class TestComments(unittest.TestCase):
    def testAllComment(self):
        code = '# comment'
        expected = [
            Token('<start file>', 1),
            Token('<end file>', 2)
        ]
        actual = tokenize(code)
        self.assertEqual(actual, expected)
    
    def testTwoComments(self):
        code = '# comment1\n# comment2'
        expected = [
            Token('<start file>', 1),
            Token('<newline>', 2),
            Token('<end file>', 3)
        ]
        actual = tokenize(code)
        self.assertEqual(actual, expected)
    
    def testAnnotatedLine(self):
        code = '123 # a number'
        expected = [
            Token('<start file>', 1),
            Token('<number>', 1, 1, 4, 123),
            Token('<end file>', 2)
        ]
        actual = tokenize(code)
        self.assertEqual(actual, expected)
    
    def testAnnotatedMultiLine(self):
        code = '14 # a number\nvar#an identifier'
        expected = [
            Token('<start file>', 1),
            Token('<number>', 1, 1, 3, 14),
            Token('<newline>', 2),
            Token('<identifier>', 2, 1, 4, 'var'),
            Token('<end file>', 3)
        ]
        actual = tokenize(code)
        self.assertEqual(actual, expected)
    
    def testCommentInString(self):
        code = '"hello # comment"'
        expected = [
            Token('<start file>', 1),
            Token('<string>', 1, 1, len(code)+1, 'hello # comment'),
            Token('<end file>', 2)
        ]
        actual = tokenize(code)
        self.assertEqual(actual, expected)

if __name__ == '__main__':
    unittest.main()
