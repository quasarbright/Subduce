'''
This file contains the class structure for the parse tree.
The only functionality is __str__ functions.
There is type enforcement, fields, and inheritance.
'''
from typing import List
from typing import Union
import lexer
from lexer import Token

# expressions
class Expression:
    def __repr__(self):
        return f'{type(self).__name__}: {self}'

class Atom(Expression):
    '''An abstract class representing an atomic expression
    '''
    def __init__(self, token: Token):
        self.token = token
        self.value = token.value
        if self.value is None:
            raise ValueError('Atom value cannot be None') # TODO change
        
    def assertType(self, targetType: type):
        assert type(self.value) == targetType
    
    def __str__(self):
        return str(self.value)
    
    def __eq__(self, other: 'Atom'):
        return self.value == other.value

class Number(Atom):
    '''Represents int or float expression
    '''
    def __init__(self, token: Token):
        super().__init__(token)

class String(Atom):
    '''Represents a string expression
    '''
    def __init__(self, token: Token):
        super().__init__(token)

class Boolean(Atom):
    '''Represents a boolean expression
    '''
    def __init__(self, token: Token):
        super().__init__(token)

class VariableReference(Atom):
    '''Represents a variable reference
    '''
    def __init__(self, token: Token):
        super().__init__(token)

class ListExpression(Expression):
    '''Represents a List expression, which contains Expressions
    '''
    def __init__(self, expressions: List[Expression]):
        # enforce list of expressions
        try:
            iter(expressions)
        except TypeError:
            raise TypeError(f'Expected a list of expressions for a list: {expressions}') # TODO change
        for expression in expressions:
            if not isinstance(expression, Expression):
                raise TypeError(f'Expected an expression for a list element: {expression}') # TODO change
        self.expressions = expressions
    
    def __str__(self):
        return str(self.expressions)

    def __eq__(self, other: 'ListExpression'):
        return self.expressions == other.expressions

class HasArguments(Expression):
    '''An expression which has arguments, like a function call or lambda function definition
    '''
    def __init__(self, arguments: 'List[VariableReference]'):
        # enforce arguments is a list of identifiers
        try:
            iter(arguments)
        except TypeError:
            raise TypeError(f'Expected a list of variable references for function arguments: {arguments}') # TODO change
        for argument in arguments:
            if not isinstance(argument, VariableReference):
                raise TypeError(f'Expected a variable reference for a function argument: {argument}') # TODO change
        self.arguments = arguments
    
    def __str__(self):
        '''Ex: [arg1, arg2]
        '''
        return str(self.arguments)

    def __eq__(self, other: 'HasArguments'):
        return self.arguments == other.arguments

class FunctionCall(Expression):
    '''Represents a function call
    '''
    def __init__(self, function: Expression, arguments: 'List[Expression]'):
        # enforce function name is an identifier
        if not isinstance(function, Expression):
            raise TypeError(f'Expected an expression for a function: {function}') # TODO change
        self.function = function
        try:
            iter(arguments)
        except TypeError:
            raise TypeError(f'Expected a list of Expressions for function call arguments: {arguments}')
        for argument in arguments:
            if not isinstance(argument, Expression):
                raise TypeError(f'Expected an Expression for function call argument: {argument}')
        self.arguments = arguments

    def __str__(self):
        '''return what the call may have looked like in code'''
        strargs = map(str, self.arguments)
        return f'({self.function} {" ".join(strargs)})'

    def __eq__(self, other: 'FunctionCall'):
        # check args
        return self.function == other.function and self.arguments == other.arguments
        
        
class LambdaDefinition(HasArguments):
    '''Represents a lambda (anonymous) function definition
    '''
    def __init__(self, arguments: 'List[VariableReference]', body: Expression):
        # enforce arguments is a list of arguments 
        super().__init__(arguments)
        if not isinstance(body, Expression):
            raise TypeError(f'Expected expression for lambda body: {body}') # TODO change
        self.body = body
    
    def __str__(self):
        strargs = map(str, self.arguments)
        return f'(lam ({" ".join(strargs)}) {self.body})'

    def __eq__(self, other: 'LambdaDefinition'):
        if self.arguments != other.arguments:
            return False
        if self.body != other.body:
            return False
        return True

# statements
class Statement:
    '''Abstract class for statements
    '''
    def __repr__(self):
        return f'{type(self).__name__}: {self}'

class Assignment(Statement):
    '''Represents an assignment statement
    '''
    def __init__(self, variable: Token, value: Expression):
        if variable.type != lexer.IDENTIFIER:
            raise TypeError(f'Expected an identifier for a variable assignemnt: {variable}') # TODO change
        self.variable = variable
        if not isinstance(value, Expression):
            raise TypeError(f'Expected an expression for a variable assignment: {value}') # TODO change
        self.value = value
    
    def __str__(self):
        return f'{self.variable.value} = {self.value}'
    
    def __eq__(self, other: 'Assignment'):
        if self.variable != other.variable:
            return False
        if self.value != other.value:
            return False
        return True
    
    def getName(self):
        return str(self.variable.value)

class FunctionSignature(FunctionCall, HasArguments):
    '''only inherits from HasArguments for the validation, really
    '''
    def __init__(self, functionName: Token, arguments: 'List[VariableReference]'):
        if functionName.type != lexer.IDENTIFIER:
            raise SyntaxError(f'Expected an identifier for a function name: {functionName}')
        if functionName.value == 'lam':
            raise SyntaxError('Function cannot be named lam')
        function = VariableReference(functionName)
        FunctionCall.__init__(self, function, arguments)
        HasArguments.__init__(self, arguments)
    
    def __str__(self):
        return FunctionCall.__str__(self)
    
    def __eq__(self, other: 'FunctionSignature'):
        return FunctionCall.__eq__(self, other)

class FunctionDefinition(Statement):
    '''Represents a function definition statement
    '''
    def __init__(self, signature: FunctionSignature, body: 'Body'):
        if not isinstance(signature, FunctionSignature):
            raise TypeError(f'Expected function call for a function signature: {signature}') # TODO change
        self.signature = signature
        if not isinstance(body, Body):
            raise TypeError(f'Expected body for a function body: {body}') # TODO change
        self.body = body

    def __str__(self):
        header = f'def {self.signature}:'
        bodyLines = str(self.body).split('\n')
        indentedLines = [f'\t{line}' for line in bodyLines]
        ans = f'{header}\n{indentedLines}'
        return ans

    def __eq__(self, other: 'FunctionDefinition'):
        if not self.signature == other.signature:
            return False
        if not self.body == other.body:
            return False
        return True
    
    def getName(self) -> str:
        '''get this function's name
        '''
        return str(self.signature.function)

class Return(Statement):
    '''Represents a return Statement
    '''
    def __init__(self, value: Expression):
        if not isinstance(value, Expression):
            raise TypeError(f'Expected an expression for a return statement: {value}') # TODO change
        self.value = value
    
    def __str__(self):
        return f'return {self.value}'

    def __eq__(self, other: 'Return'):
        return self.value == other.value

class Print(Statement):
    '''Represents a print Statement
    '''
    def __init__(self, value: Expression):
        if not isinstance(value, Expression):
            raise TypeError(f'Expected an expression for a return statement: {value}') # TODO change
        self.value = value
    
    def __str__(self):
        return f'print {self.value}'
    
    def __eq__(self, other: 'Print'):
        return self.value == other.value

class Body:
    '''Represents a list of statements.
    The source code is a Body, the inside of a function is a Body
    '''
    def __init__(self, statements: 'List[Statement]', indentationLevel: int = 1):
        try:
            iter(statements)
        except TypeError:
            raise TypeError(f'Expected list of statements for a body: {statements}')
        for statement in statements:
            if not isinstance(statement, Statement):
                raise TypeError(f'Expected a statement for a body: {statement}')
        self.statements = statements
        self.indentationLevel = indentationLevel
    
    def __str__(self):
        '''try to reproduce source code
        '''
        return '\n'.join(self.statements)
    
    def __repr__(self):
        return f'{type(self).__name__}: {self}'
    
    def __eq__(self, other: 'Body'):
        return self.statements == other.statements

class MainBody(Body):
    def __init__(self, statements: 'List[Statement]'):
        super().__init__(statements, indentationLevel=0)

'''
precompile a scope tree with chronological order
'''
