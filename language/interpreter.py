from typing import List
from typing import Union
from lexer import Token
from parseTree import *

# python "atomic" type
# this is the indivisible type that subduce expressions will break down to
PyAtom = Union[int, float, str, bool, function]  # "atomic" type

class SFunction:
    '''Subduce function
    '''
    pass

class SList:
    '''Subduce list
    '''
    pass

class Context:
    '''keeps track of nested variable scopes and variable values
    '''
    pass

def evaluateAtom(expression: Atom) -> PyAtom:
    pass

def evaluateVariableReference(context: Context, expression: VariableReference) -> PyAtom:
    pass

def evaluateList(context: Context, expression: ListExpression) -> List[PyAtom]:
    pass

def evaluateLambdaDefinition(context: Context, expression) -> function:
    pass

def evaluateExpression(context: Context, expression: Expression):
    pass
