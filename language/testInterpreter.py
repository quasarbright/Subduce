import unittest
import lexer
import parseTree
from interpreter import *

number = parseTree.Number(lexer.Token(lexer.NUMBER, 1, 1, 2, 123))
string = parseTree.String(lexer.Token(lexer.STRING, 1, 1, 2, 'hello'))
boolean = parseTree.Boolean(lexer.Token(lexer.BOOLEAN, 1, 1, 2, True))
variable = parseTree.VariableReference(lexer.Token(lexer.IDENTIFIER, 1, 1, 2, 'var'))
variable2 = parseTree.VariableReference(lexer.Token(lexer.IDENTIFIER, 1, 1, 2, 'var2'))
lam = parseTree.LambdaDefinition([variable, variable2], number)
listSimple = parseTree.ListExpression([number, variable, variable2, number])
listComplex = parseTree.ListExpression([number, number, listSimple])

contextSimple = Context()
contextSimple.setVar('var', 'No Title')
contextSimple.setVar('var2', 'Asymmetry')

class TetsEvaluators(unittest.TestCase):
    def testAtoms(self):
        self.assertEqual(evaluateAtom(number), 123)
        self.assertEqual(evaluateAtom(string), 'hello')
        self.assertEqual(evaluateAtom(boolean), True)
    def testListSimple(self):
        self.assertEqual(evaluateList(contextSimple, listSimple), [123, 'No Title', 'Asymmetry', 123])
    def testListComplex(self):
        self.assertEqual(evaluateList(contextSimple, listComplex), [123, 123, [123, 'No Title', 'Asymmetry', 123]])

if __name__ == '__main__':
    unittest.main()
