import unittest
from lexer import *

class TestExpression(unittest.TestCase):
    def testAddition(self):
        code = '(+ 1 24)'
        expected = [
            Token('<start file>', 1, 0, 1),
            Token('<start function>', 1, 1, 2),
            Token('<identifier>', 1, 2, 3, '+'),
            Token('<number>', 1, 4, 5, 1),
            Token('<number>', 1, 6, 8, 24, 24),
            Token('<end function>', 1, 8, 9),
            Token('<end file>', 1, 9, 10)
        ]
        self.assertEqual(tokenize(code), expected)
    def testAssignment(self):
        code = 'var = '
