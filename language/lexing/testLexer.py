import unittest
from lexer import *

class TestExpression(unittest.TestCase):
    def testInt(self):
        code = '123'
        expected = [
            Token(START_FILE, 1),
            Token(NUMBER, 1, 1, 4, 123),
            Token(END_FILE, 2)
        ]
        actual = tokenize(code)
        self.assertEqual(actual, expected)
    
    def testNegativeInt(self):
        code = '-123'
        expected = [
            Token(START_FILE, 1),
            Token(NUMBER, 1, 1, 5, -123),
            Token(END_FILE, 2)
        ]
        actual = tokenize(code)
        self.assertEqual(actual, expected)
    
    def testFloat(self):
        code = '3.14'
        expected = [
            Token(START_FILE, 1),
            Token(NUMBER, 1, 1, 5, 3.14),
            Token(END_FILE, 2)
        ]
        actual = tokenize(code)
        self.assertEqual(actual, expected)
    
    def testNegativeFloat(self):
        code = '-6.28'
        expected = [
            Token(START_FILE, 1),
            Token(NUMBER, 1, 1, 6, -6.28),
            Token(END_FILE, 2)
        ]
        actual = tokenize(code)
        self.assertEqual(actual, expected)
    
    def testNegativeStartPoint(self):
        code = '-.28'
        expected = [
            Token(START_FILE, 1),
            Token(NUMBER, 1, 1, 5, -.28),
            Token(END_FILE, 2)
        ]
        actual = tokenize(code)
        self.assertEqual(actual, expected)
    
    def testNegativeEndPoint(self):
        code = '-6.'
        expected = [
            Token(START_FILE, 1),
            Token(NUMBER, 1, 1, 4, -6.),
            Token(END_FILE, 2)
        ]
        actual = tokenize(code)
        self.assertEqual(actual, expected)
    
    def testString(self):
        code = '"hello"'
        expected = [
            Token(START_FILE, 1),
            Token(STRING, 1, 1, 8, "hello"),
            Token(END_FILE, 2)
        ]
        self.assertEqual(tokenize(code), expected)
    
    def testEscapeQuote(self):
        code = r'"quote \" ok"'
        expected = [
            Token(START_FILE, 1),
            Token(STRING, 1, 1, len(code)+1, 'quote " ok'),
            Token(END_FILE, 2)
        ]
        actual = tokenize(code)
        self.assertEqual(actual, expected)
    
    def testEscapeBackslash(self):
        code = r'"quote \\\" ok"'
        expected = [
            Token(START_FILE, 1),
            Token(STRING, 1, 1, len(code)+1, 'quote \\" ok'),
            Token(END_FILE, 2)
        ]
        actual = tokenize(code)
        self.assertEqual(actual, expected)
    
    def testEscapeCharacter(self):
        code = r'"\a\n\t\r\b"'
        expected = [
            Token(START_FILE, 1),
            Token(STRING, 1, 1, len(code)+1, '\a\n\t\r\b'),
            Token(END_FILE, 2)
        ]
        actual = tokenize(code)
        self.assertEqual(actual, expected)
    
    def testBool(self):
        code = 'true'
        expected = [
            Token(START_FILE, 1),
            Token(BOOLEAN, 1, 1, 5, True),
            Token(END_FILE, 2)
        ]
        self.assertEqual(tokenize(code), expected)
        code = 'false'
        expected = [
            Token(START_FILE, 1),
            Token(BOOLEAN, 1, 1, 6, False),
            Token(END_FILE, 2)
        ]
        self.assertEqual(tokenize(code), expected)
    
    def testAddition(self):
        code = '(+ 1 24)'
        expected = [
            Token(START_FILE, 1),
            Token(START_FUNCTION, 1, 1, 2),
            Token(IDENTIFIER, 1, 2, 3, '+'),
            Token(NUMBER, 1, 4, 5, 1),
            Token(NUMBER, 1, 6, 8, 24),
            Token(END_FUNCTION, 1, 8, 9),
            Token(END_FILE, 2)
        ]
        actual = tokenize(code)
        self.assertEqual(actual, expected)

    def testMultiline(self):
        code = '(+\n\t1\n\t2)'
        expected = [
            Token(START_FILE, 1),
            Token(START_FUNCTION, 1, 1, 2),
            Token(IDENTIFIER, 1, 2, 3, '+'),
            Token(NEWLINE, 2),
            Token(INDENT, 2),
            Token(NUMBER, 2, 2, 3, 1),
            Token(NEWLINE, 3),
            Token(NUMBER, 3, 2, 3, 2),
            Token(END_FUNCTION, 3, 3, 4),
            Token(END_FILE, 4)
        ]
        actual = tokenize(code)
        self.assertEqual(actual, expected)
    
    def testOneCharacter(self):
        code = '1'
        expected = [
            Token(START_FILE, 1),
            Token(NUMBER, 1, 1, 2, 1),
            Token(END_FILE, 2)
        ]
        actual = tokenize(code)
        self.assertEqual(actual, expected)
    
    def testEmpty(self):
        code = ''
        expected = [
            Token(START_FILE, 1),
            Token(END_FILE, 2)
        ]
        actual = tokenize(code)
        self.assertEqual(actual, expected)
    
    def testKeyWords(self):
        code = 'def return print'
        expected = [
            Token(START_FILE, 1),
            Token(KEYWORD, 1, 1, 4, 'def'),
            Token(KEYWORD, 1, 5, 11, 'return'),
            Token(KEYWORD, 1, 12, len(code)+1, 'print'),
            Token(END_FILE, 2)
        ]
        actual = tokenize(code)
        self.assertEqual(actual, expected)
    
    def testStartFunNoSpace(self):
        code = '(+ 1(* 2 33))'
        expected = [
            Token(START_FILE, 1),
            Token(START_FUNCTION, 1, 1, 2),
            Token(IDENTIFIER, 1, 2, 3, '+'),
            Token(NUMBER, 1, 4, 5, 1),
            Token(START_FUNCTION, 1, 5, 6),
            Token(IDENTIFIER, 1, 6, 7, '*'),
            Token(NUMBER, 1, 8, 9, 2),
            Token(NUMBER, 1, 10, 12, 33),
            Token(END_FUNCTION, 1, 12, 13),
            Token(END_FUNCTION, 1, 13, 14),
            Token(END_FILE, 2)
        ]
        actual = tokenize(code)
        self.assertEqual(actual, expected)

class TestFunctionDefinition(unittest.TestCase):
    def testHeader(self):
        code = 'def (fun x y):'
        expected = [
            Token(START_FILE, 1),
            Token(KEYWORD, 1, 1, 4, 'def'),
            Token(START_FUNCTION, 1, 5, 6),
            Token(IDENTIFIER, 1, 6, 9, 'fun'),
            Token(IDENTIFIER, 1, 10, 11, 'x'),
            Token(IDENTIFIER, 1, 12, 13, 'y'),
            Token(END_FUNCTION, 1, 13, 14),
            Token(END_SIGNATURE, 1, 14, 15),
            Token(END_FILE, 2)
        ]
        actual = tokenize(code)
        self.assertEqual(actual, expected)

class TestIndentation(unittest.TestCase):
    def testFirstLineIndented(self):
        code = '\th'
        expected = [
            Token(START_FILE, 1),
            Token(INDENT, 1),
            Token(IDENTIFIER, 1, 2, 3, 'h'),
            Token(END_FILE, 2)
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
            Token(START_FILE, 1),
            Token(NUMBER, 1, 1, 2, 0),
            
            Token(NEWLINE, 2),
            Token(INDENT, 2),
            Token(NUMBER, 2, 2, 3, 1),
            
            Token(NEWLINE, 3),
            Token(NUMBER, 3, 2, 3, 1),
            
            Token(NEWLINE, 4),
            Token(INDENT, 4),
            Token(NUMBER, 4, 3, 4, 2),

            Token(NEWLINE, 5),
            Token(UNINDENT, 5),
            Token(NUMBER, 5, 2, 3, 1),

            Token(NEWLINE, 6),
            Token(INDENT, 6),
            Token(INDENT, 6),
            Token(NUMBER, 6, 4, 5, 3),

            Token(NEWLINE, 7),
            Token(UNINDENT, 7),
            Token(UNINDENT, 7),
            Token(NUMBER, 7, 2, 3, 1),

            Token(NEWLINE, 8),
            Token(UNINDENT, 8),
            Token(NUMBER, 8, 1, 2, 0),

            Token(NEWLINE, 9),
            Token(INDENT, 9),
            Token(NUMBER, 9, 2, 3, 1),
            
            Token(END_FILE, 10)
        ]
        actual = tokenize(code)
        self.assertEqual(actual, expected)
    
    def testIgnoreCommentIndent(self):
        code = '0\n\t#1\n0'
        expected = [
            Token(START_FILE, 1),
            Token(NUMBER, 1, 1, 2, 0),
            Token(NEWLINE, 2),
            Token(NEWLINE, 3),
            Token(NUMBER, 3, 1, 2, 0),
            Token(END_FILE, 4)
        ]
        actual = tokenize(code)
        self.assertEqual(actual, expected)
    
    def testBlankLineNoUnindent(self):
        code = '\t1\n\n\t1'
        '''
            1
        
            1
        '''
        expected = [
            Token(START_FILE, 1),
            Token(INDENT, 1),
            Token(NUMBER, 1, 2, 3, 1),
            Token(NEWLINE, 2),
            Token(NEWLINE, 3),
            Token(NUMBER, 3, 2, 3, 1),
            Token(END_FILE, 4)
        ]
        actual = tokenize(code)
        self.assertEqual(actual, expected)
    
class TestAssignment(unittest.TestCase):
    def testAssignment(self):
        code = 'var = 2'
        expected = [
            Token(START_FILE, 1),
            Token(IDENTIFIER, 1, 1, 4, 'var'),
            Token(EQUALS, 1, 5, 6),
            Token(NUMBER, 1, 7, 8, 2),
            Token(END_FILE, 2)
        ]
        self.assertEqual(tokenize(code), expected)

class TestComments(unittest.TestCase):
    def testAllComment(self):
        code = '# comment'
        expected = [
            Token(START_FILE, 1),
            Token(END_FILE, 2)
        ]
        actual = tokenize(code)
        self.assertEqual(actual, expected)
    
    def testTwoComments(self):
        code = '# comment1\n# comment2'
        expected = [
            Token(START_FILE, 1),
            Token(NEWLINE, 2),
            Token(END_FILE, 3)
        ]
        actual = tokenize(code)
        self.assertEqual(actual, expected)
    
    def testAnnotatedLine(self):
        code = '123 # a number'
        expected = [
            Token(START_FILE, 1),
            Token(NUMBER, 1, 1, 4, 123),
            Token(END_FILE, 2)
        ]
        actual = tokenize(code)
        self.assertEqual(actual, expected)
    
    def testAnnotatedMultiLine(self):
        code = '14 # a number\nvar#an identifier'
        expected = [
            Token(START_FILE, 1),
            Token(NUMBER, 1, 1, 3, 14),
            Token(NEWLINE, 2),
            Token(IDENTIFIER, 2, 1, 4, 'var'),
            Token(END_FILE, 3)
        ]
        actual = tokenize(code)
        self.assertEqual(actual, expected)
    
    def testCommentInString(self):
        code = '"hello # comment"'
        expected = [
            Token(START_FILE, 1),
            Token(STRING, 1, 1, len(code)+1, 'hello # comment'),
            Token(END_FILE, 2)
        ]
        actual = tokenize(code)
        self.assertEqual(actual, expected)

if __name__ == '__main__':
    unittest.main()
