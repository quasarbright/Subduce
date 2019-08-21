from typing import List
from typing import Union
from lexer import Token
from parseTree import *


class SFunction:
    '''Subduce function
    '''
    def __init__(self, scopes: 'ScopeStack'):
        pass
class SList:
    '''Subduce list
    '''
    pass

# built-in variables and functions
BUILTINS = {}


# python "atomic" type
# this is the indivisible type that subduce expressions will break down to
PyAtom = Union[int, float, str, bool, SFunction]
PyValue = Union[PyAtom, SList]

class Stack:
    def __init__(self):
        self.elements = []

    def isEmpty(self) -> bool:
        return len(self) == 0

    def push(self, e):
        self.elements.append(e)
    
    def pop(self):
        if self.isEmpty():
            raise RuntimeError('Popped empty stack')
        else:
            return self.elements.pop()
    
    def peek(self):
        if self.isEmpty():
            raise RuntimeError('Peeked empty stack')
        else:
            return self.elements[-1]
    
    def __len__(self):
        return len(self.elements)

class ScopeStack(Stack):
    '''Stack of variable scopes
    A variable scope is a dictionary from strings to PyValues
    '''
    def __init__(self):
        super().__init__()
        # builtins and globals
        self.push(BUILTINS)
        self.push({})
    
    def pop(self) -> dict:
        # can't pop global scope
        if len(self) <= 2:
            raise RuntimeError('Popped global scope')
        else:
            return super().pop(self)

    def getVar(self, varName: str) -> PyValue:
        '''Returns the value of the referenced variable
        Prioritizes the most local scope
        '''
        for scope in self.elements[::-1]:
            if varName in scope:
                return scope[varName]
        raise UnboundLocalError(f'Variable {varName} referenced before assignment')
    
    def setVar(self, varName: str, value: PyValue) -> None:
        '''sets a variable in the most local scope
        '''
        scope = self.elements[-1]
        if varName in scope:
            raise RuntimeError('Cannot override variables in the same scope')
        else:
            scope[varName] = value

class CallStack(Stack):
    '''stack of function calls
    '''
    pass

class Context:
    '''scope stack and call stack
    '''
    def __init__(self, scopes: ScopeStack = None, callStack: CallStack = None):
        self.scopes = scopes
        if scopes is None:
            self.scopes = ScopeStack()
        self.callStack = callStack
        if callStack is None:
            self.callStack = CallStack()
        
    def pushScope(self, scope: dict) -> None:
        self.scopes.push(scope)
        
    def popScope(self) -> dict:
        return self.scopes.pop()
        
    def peekScope(self) -> dict:
        return self.scopes.peek()
    
    def getVar(self, varName: str) -> PyValue:
        '''Returns the value of the referenced variable
        Prioritizes the most local scope
        '''
        return self.scopes.getVar(varName)
    
    def setVar(self, varName: str, value: PyValue) -> None:
        '''sets the variable in the most local scope
        '''
        self.scopes.setVar(varName, value)

def evaluateAtom(expression: Atom) -> PyAtom:
    return expression.value

def evaluateVariableReference(context: Context, expression: VariableReference) -> PyValue:
    varName = expression.value
    return context.getVar(varName)

def evaluateList(context: Context, expression: ListExpression) -> List[PyValue]:
    expressions = expression.expressions
    return [evaluateExpression(context, e) for e in expressions]

def evaluateLambdaDefinition(context: Context, expression) -> SFunction:
    pass

def evaluateFunctionCall(context: Context, expression: FunctionCall) -> PyValue:
    pass

def evaluateExpression(context: Context, expression: Expression) -> PyValue:
    typeToFunc = {
        ListExpression: evaluateList,
        LambdaDefinition: evaluateLambdaDefinition,
        VariableReference: evaluateVariableReference,
        FunctionCall: evaluateFunctionCall
    }
    if isinstance(expression, Atom) and not isinstance(expression, VariableReference):
        return evaluateAtom(expression)
    else:
        evaluator = typeToFunc[type(expression)]
        return evaluator(context, expression)

def runBody(context: Context, body: Body):
    '''Stops executing on return
    '''
    pass

def runMainBody(body: MainBody):
    context = Context()
