from typing import List
from typing import Union
from lexer import Token
from parseTree import *


class SFunction:
    '''Subduce function
    '''
    def __init__(self, scopes: 'ScopeStack', func: Union[function, FunctionDefinition]):
        self.func = func
class SList:
    '''Subduce list
    '''
    def __init__(self, values: List['PyValue']):
        self.values = values

def plus(arguments: List['PyAtom']):
    '''this is only to be called once arguments are evaluated to atoms
    '''
    sum = 0
    for argument in arguments:
        if not (isinstance(argument, int) or isinstance(argument, float)):
            raise TypeError("plus expects numbers")
        else:
            sum += argument
    return sum


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
    
    def shallowCopy(self):
        '''Clone this stack shallowly (contents are not cloned)
        '''
        ans = Stack()
        ans.elements = self.elements[:]
        return ans

class ScopeStack(Stack):
    '''Stack of variable scopes
    A variable scope is a dictionary from strings to Union[VariableReference, FunctionDefinition]
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
    
    def getVar(self, varName: str) -> Union[Assignment, FunctionDefinition]:
        '''Returns the value of the referenced variable
        Prioritizes the most local scope
        '''
        for scope in self.elements[::-1]:
            if varName in scope:
                return scope[varName]
        raise UnboundLocalError(f'Variable {varName} referenced before assignment')
    
    def setVar(self, varName: str, value: Union[VariableReference, FunctionDefinition]) -> None:
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

class ScopedBody(Body):
    '''A body with variable scope.
    Takes in an unscoped body and a scope stack containing the body.
    Clones the scope stack, adds local variables, and recursively converts contained bodies to scoped bodies.
    '''
    def __init__(self, mainBody: MainBody, scopeStack: ScopeStack):
        super().__init__(mainBody.statements, mainBody.indentationLevel)
        self.scopeStack = scopeStack.shallowCopy()
        for statement in self.statements:
            if isinstance(statement, Assignment) or isinstance(statement, FunctionDefinition):
                name = statement.getName()
                scopeStack.push({})
                scopeStack.setVar(name, statement)
            if isinstance(statement, FunctionDefinition):
                # go into the function definition and convert its body to a scoped body
                # wait a minute, how do you handle function arguments?
                ### left off here

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
    # map from Token to scope stack
    # will contain all variable assignments and function definitions
    # remember to add function arguments in
    # wait what about return statements
    # maybe you should do map from body to scope and do the rest live from there
    # If you want importing, two files can have the same token in the same place and they'd hash to the same thing
    # Try not to do a map. Maybe create an intermediate class that has a body and a scope
    scopeMap = {}
    context = Context()
