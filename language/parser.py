from typing import List
import lexer
from lexer import Token

# expressions
class Expression:
    pass

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

class List(Expression):
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

class HasArguments(Expression):
    '''An expression which has arguments, like a function call or lambda function definition
    '''
    def __init__(self, arguments: 'List[Token]'):
        # enforce arguments is a list of identifiers
        try:
            iter(arguments)
        except TypeError:
            raise TypeError(f'Expected a list of identifiers for function arguments: {arguments}') # TODO change
        for argument in arguments:
            if not isinstance(argument, Token):
                raise TypeError(f'Expected a token for a function argument: {argument}') # TODO change
            elif argument.type != lexer.IDENTIFIER:
                raise TypeError(f'Expected an identifier for a function argument: {argument}') # TODO change
        self.arguments = arguments
    
    def __str__(self):
        '''Ex: [arg1, arg2]
        '''
        return ' '.join([argument.value for argument in arguments])

class FunctionCall(Expression):
    '''Represents a function call
    '''
    def __init__(self, functionName: Token, arguments: 'List[Token]'):
        # enforce arguments is a list of identifiers
        super().__init__(self, arguments)

        # enforce function name is an identifier
        if not isinstance(functionName, Token):
            raise TypeError(f'Expected a token for a function name: {functionName}') # TODO change
        elif functionName.type != lexer.IDENTIFIER:
            raise TypeError(f'Expected an identifier for a function name: {functionName}') # TODO change
        self.functionName = functionName

    def __str__(self):
        '''return what the call may have looked like in code'''
        return f'({self.functionName} {super().__str__()})'        
        
        
class LambdaDefinition(Expression):
    '''Represents a lambda (anonymous) function definition
    '''
    def __init__(self, arguments: 'List[Token]', body: Expression):
        # enforce arguments is a list of 
        super().__init__(arguments)
        if not isinstance(body, Expression):
            raise TypeError(f'Expected expression for lambda body: {body}') # TODO change
        self.body = body
    
    def __str__(self):
        return f'(lam ({super().__str__()}) {self.body()})'

# statements
class Statement:
    '''Abstract class for statements
    '''
    pass

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
        return f'{variable.value} = {value}'

class FunctionDefinition(Statement):
    '''Represents a function definition statement
    '''
    def __init__(self, signature: FunctionCall, body: Body):
        if not isinstance(signature, FunctionCall):
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

class Return(Statement):
    '''Represents a return Statement
    '''
    def __init__(self, value: Expression):
        if not isinstance(value, Expression):
            raise TypeError(f'Expected an expression for a return statement: {value}') # TODO change
        self.value = value
    
    def __str__(self):
        return f'return {self.value}'

class Print(Statement):
    '''Represents a print Statement
    '''
    def __init__(self, value: Expression):
        if not isinstance(value, Expression):
            raise TypeError(f'Expected an expression for a return statement: {value}') # TODO change
        self.value = value
    
    def __str__(self):
        return f'print {self.value}'

class Body:
    '''Represents a list of statements.
    The source code is a Body, the inside of a function is a Body
    '''
    def __init__(self, statements: 'List[Statement]'):
        try:
            iter(statements)
        except TypeError:
            raise TypeError(f'Expected list of statements for a body: {statements}')
        for statement in statements:
            if not isinstance(statement, Statement):
                raise TypeError(f'Expected a statement for a body: {statement}')
        self.statements = statements
    
    def __str__(self):
        '''try to reproduce source code
        '''
        return '\n'.join(self.statements)
