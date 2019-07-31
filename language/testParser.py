import unittest
from lexer import *
from parser import *
from parseTree import *

identifier = Token(lexer.IDENTIFIER, 1, 1, 2, 'var')
number = Token(lexer.NUMBER, 1, 1, 2, 123)
equals = Token(lexer.EQUALS, 1, 1, 2)
string = Token(lexer.STRING, 1, 1, 2, 'hello')

class TestAssignment(unittest.TestCase):
    def testNumberAssignment(self):
        tokens = [
            identifier,
            equals,
            number
        ]
        expected = Assignment(identifier, number)
        actual = parseTokens(tokens)
        self.assertEqual(actual, expected)
    def testStringAssignment(self):
        tokens = [
            identifier,
            equals,
            string
        ]
        expected = Assignment(identifier, string)
        actual = parseTokens()
