from typing import List
from lexer import *
from parseTree import *



class TokenStream:
    def __init__(self, tokens: List[Token]):
        self.tokens = tokens
        if len(tokens) == 0:
            self.done = True
        else:
            self.done = False
            self.index = 0
            self.current = self.tokens[self.index]
    def peek(self) -> Token:
        if self.done:
            raise RuntimeError('cant peek done token stream')
        else:
            return self.current

    def advance(self) -> None:
        if self.done:
            raise RuntimeError('cant advance done token stream')
        else:
            self.index += 1
            if self.index >= len(self.tokens):
                self.done = True
            else:
                self.current = self.tokens[self.index]


def parseAtom(stream: TokenStream) -> Atom:
    current = stream.peek()
    ans = None
    assert current.type in [BOOLEAN, NUMBER, STRING]
    if current.type == BOOLEAN:
        ans = Boolean(current)
    elif current.type == NUMBER:
        ans = Number(current)
    elif current.type == STRING:
        ans = String(current)
    assert ans is not None
    stream.advance()
    return ans

def parseList(stream: TokenStream) -> ListExpression:
    # TODO multiline
    current = stream.peek()
    assert current.type == START_LIST
    stream.advance()
    if stream.done:
        raise SyntaxError(f'Expected expression or {typeToChar[END_LIST]}')
    expressions = []
    while True:
        current = stream.peek()
        if current.type == END_LIST:
            stream.advance()
            break
        else:
            # assumes this will advance stream
            expression = parseExpression(stream)
            expressions.append(expression)
        if stream.done:
            raise SyntaxError(f'Expected expression or {typeToChar[END_LIST]}')
    return ListExpression(expressions)

def parseLambda(stream: TokenStream) -> LambdaDefinition:
    '''expects to be called from within parseFunctionCall
    and be past the START_FUNCTION token, on the lam identifier'''
    # TODO multiline
    current = stream.peek()
    assert current.type == IDENTIFIER and current.value == 'lam'
    stream.advance()
    if stream.done:
        raise SyntaxError(f'Expected lambda arguments in {typeToChar[START_FUNCTION]}{typeToChar[END_FUNCTION]}')
    current = stream.peek()
    if current.type != START_FUNCTION:
        raise SyntaxError(f'Expected lambda arguments in {typeToChar[START_FUNCTION]}{typeToChar[END_FUNCTION]}: {current}')
    stream.advance()
    if stream.done:
        raise SyntaxError(f'Expected argument name or {typeToChar[END_FUNCTION]}')
    # get list of argument names
    argumentVars = [] # variable references
    while True:
        current = stream.peek()
        if current.type == END_FUNCTION:
            stream.advance()
            break
        # make sure it's an identifier
        if current.type != IDENTIFIER:
            raise SyntaxError(f'Expected argument name or {typeToChar[END_FUNCTION]}: {current}')
        argumentReference = VariableReference(current)
        argumentVars.append(argumentReference)
        stream.advance()
        if stream.done:
            raise SyntaxError(f'Expected argument name or {typeToChar[END_FUNCTION]}')
    if len(argumentVars) == 0:
        raise SyntaxError('Functions must have at least one argument')
    # get return expression
    returnedExpression = parseExpression(stream)
    if stream.done:
        raise SyntaxError(f'Expected {typeToChar[END_FUNCTION]}')
    current = stream.peek()
    if current.type != END_FUNCTION:
        raise SyntaxError(f'Expected {typeToChar[END_FUNCTION]}')
    stream.advance()
    return LambdaDefinition(argumentVars, returnedExpression)


def parseFunctionCall(stream: TokenStream) -> FunctionCall:
    # TODO multiline
    current = stream.peek()
    assert current.type == START_FUNCTION
    stream.advance()
    if stream.done:
        raise SyntaxError('Expected function')
    current = stream.peek()
    # this might actually be a lambda definition
    if current.value == 'lam':
        return parseLambda(stream)
    # this is a function call, the function is the current expression
    function = parseExpression(stream)
    # the stream should have been advanced by parseExpression
    if stream.done:
        raise SyntaxError('Expected an expression')
    arguments = [] # expressions
    while True:
        current = stream.peek()
        if current.type == END_FUNCTION:
            stream.advance()
            break
        else:
            # assumes this will advance the stream
            expression = parseExpression(stream)
            arguments.append(expression)
        if stream.done:
            raise SyntaxError(f'Expected expression or {typeToChar[END_FUNCTION]}')
    return FunctionCall(function, arguments)

def parseVariableReference(stream: TokenStream) -> VariableReference:
    current = stream.peek()
    assert current.type == IDENTIFIER
    ans = VariableReference(current)
    stream.advance()
    return ans

def parseExpression(stream: TokenStream) -> Expression:
    # TODO multiline
    typeToFunc = {
        START_FUNCTION: parseFunctionCall,
        START_LIST: parseList,
        NUMBER: parseAtom,
        STRING: parseAtom,
        BOOLEAN: parseAtom,
        IDENTIFIER: parseVariableReference
    }
    current = stream.peek()
    if current.type not in typeToFunc:
        raise SyntaxError(f'Expected expression: {current}')
    else:
        parsingFunction = typeToFunc[current.type]
        return parsingFunction(stream)


def parseAssignment(stream: TokenStream) -> Assignment:
    # TODO multiline
    current = stream.peek()
    assert current.type == IDENTIFIER
    variable = current
    stream.advance()
    if stream.done:
        raise SyntaxError(f'Expected an {typeToChar[EQUALS]}')
    current = stream.peek()
    if current.type != EQUALS:
        raise SyntaxError(f'Expected an {typeToChar[EQUALS]}')
    stream.advance()
    if stream.done:
        raise SyntaxError('Expected an expression')
    value = parseExpression(stream)
    ans = Assignment(variable, value)
    return ans

def parseFunctionSignature(stream: TokenStream) -> FunctionSignature:
    '''parses from "(" to ")"
    '''
    # TODO multiline
    current = stream.peek()
    assert current.type == START_FUNCTION
    stream.advance()
    if stream.done:
        raise SyntaxError('Expected a function name')
    current = stream.peek()
    if current.type != IDENTIFIER:
        raise SyntaxError(f'Expected a function name: {current}')
    functionName = current
    stream.advance()
    if stream.done:
        raise SyntaxError('Expected a function argument name')
    argumentNames = []
    while True:
        current = stream.peek()
        if current.type == IDENTIFIER:
            argumentNames.append(VariableReference(current))
        elif current.type == END_FUNCTION:
            stream.advance()
            break
        else:
            raise SyntaxError(f'Expected a function argument name or {typeToChar[END_FUNCTION]}: {current}')
        stream.advance()
        if stream.done:
            raise SyntaxError(f'Expected a function argument name or {typeToChar[END_FUNCTION]}')
    if len(argumentNames) == 0:
        raise SyntaxError('Functions must have at least one argument')
    return FunctionSignature(functionName, argumentNames)


def parseFunctionDefinition(stream: TokenStream) -> FunctionDefinition:
    # TODO account for multiline statements
    current = stream.peek()
    assert current.type == KEYWORD and current.value == 'def'
    stream.advance()
    if stream.done:
        raise SyntaxError('Expected a function signature')
    current = stream.peek()
    if current.type != START_FUNCTION:
        raise SyntaxError(f'Expected a function signature: {current}')
    signature = parseFunctionSignature(stream)
    if stream.done:
        raise SyntaxError(f'Expected a {typeToChar[END_SIGNATURE]}')
    current = stream.peek()
    if current.type != END_SIGNATURE:
        raise SyntaxError(f'Expected a {typeToChar[END_SIGNATURE]}: {current}')
    stream.advance()
    if stream.done:
        raise SyntaxError('Expected a newline')
    current = stream.peek()
    if current.type != NEWLINE:
        raise SyntaxError(f'Expected a newline: {current}')
    stream.advance()
    if stream.done:
        raise SyntaxError('Expected an indent')
    current = stream.peek()
    if current.type != INDENT:
        raise SyntaxError(f'Expected an indent: {current}')
    # don't advance because function body expects to start on an indent
    body = parseBody(stream)
    return FunctionDefinition(signature, body)

def parsePRHelp(stream: TokenStream, kw: str) -> 'Union[Print, Return]':
    '''parse a print or return statement
    kw should be either 'print' or 'return'
    '''
    # TODO multiline
    assert kw in ['print', 'return']
    current = stream.peek()
    assert current.type == KEYWORD and current.value == kw
    stream.advance()
    if stream.done:
        raise SyntaxError('Expected an expression')
    expression = parseExpression(stream)
    if kw == 'print':
        ans = Print(expression)
    else:
        ans = Return(expression)
    return ans

def parsePrint(stream: TokenStream) -> Print:
    # TODO multiline
    return parsePRHelp(stream, 'print')

def parseReturn(stream: TokenStream) -> Return:
    # TODO multiline
    return parsePRHelp(stream, 'return')

def parseStatement(stream: TokenStream) -> Statement:
    # TODO multiline
    current = stream.peek()
    if current.type == IDENTIFIER:
        return parseAssignment(stream)
    elif current.type == KEYWORD:
        keyword = current.value
        if keyword == 'def':
            return parseFunctionDefinition(stream)
        elif keyword == 'print':
            return parsePrint(stream)
        elif keyword == 'return':
            return parseReturn(stream)
        else:
            raise SyntaxError(f'Expected a variable assignment, function definition, print statement, or return statement: {current}')
    else:
        raise SyntaxError(f'Expected statement: {current}')

def parseBody(stream: TokenStream) -> Body:
    '''captures from indent to unindent/eof
    '''
    # TODO account for multiline statements
    current = stream.peek()
    if current.type != INDENT:
        raise SyntaxError(f'Expected an indent: {current}')
    stream.advance()
    if stream.done:
        raise SyntaxError('Expected a variable assignment, function definition, print statement, or return statement')
    current = stream.peek()
    statements = []
    while not stream.done:
        statements.append(parseStatement(stream))
        if stream.done:
            break
        current = stream.peek()
        if current.type in [UNINDENT, END_FILE]:
            stream.advance()
            break
        if current.type != NEWLINE:
            raise SyntaxError(f'Expected newline: {current}')
        else:
            stream.advance()
    ans = Body(statements)
    return ans

def parseTokens(tokens: List[Token]) -> MainBody:
    # TODO account for multiline statements
    stream = TokenStream(tokens)
    statements = []
    while not stream.done:
        current = stream.peek()
        assert current.type != UNINDENT # should be impossible unless there is a bug
        if current.type == KEYWORD and current.value == 'return':
            raise SyntaxError(f'Unexpected return')
        elif current.type == INDENT:
            raise SyntaxError('Unexpected indent')
        elif current.type == END_FILE:
            break
        else:
            parseStatement(stream)
    ans = MainBody(statements)
    return ans
    # expect the helper function to advance the stream
    # stream.advance()

'''
maybe keep track of indentation in token stream
'''
