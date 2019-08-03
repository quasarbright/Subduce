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
    argumentNames = [] # identifiers
    while True:
        current = stream.peek()
        if current.type == END_FUNCTION:
            stream.advance()
            break
        # make sure it's an identifier
        if current.type != IDENTIFIER:
            raise SyntaxError(f'Expected argument name or {typeToChar[END_FUNCTION]}: {current}')
        argumentNames.append(current)
        stream.advance()
        if stream.done:
            raise SyntaxError(f'Expected argument name or {typeToChar[END_FUNCTION]}')
    # get return expression
    returnedExpression = parseExpression(stream)
    if stream.done:
        raise SyntaxError(f'Expected {typeToChar[END_FUNCTION]}')
    current = stream.peek()
    if current.type != END_FUNCTION:
        raise SyntaxError(f'Expected {typeToChar[END_FUNCTION]}')
    stream.advance()
    return LambdaDefinition(argumentNames, returnedExpression)


def parseFunctionCall(stream: TokenStream) -> FunctionCall:
    # TODO multiline
    current = stream.peek()
    assert current.type == START_FUNCTION
    stream.advance()
    if stream.done:
        raise SyntaxError('Expected function name')
    current = stream.peek()
    if current.type != IDENTIFIER:
        raise SyntaxError('Expected function name')
    name = current
    if name.value == 'lam':
        return parseLambda(stream)

    stream.advance()
    if stream.done:
        raise SyntaxError('Expected expression')
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
    return FunctionCall(name, arguments)


def parseExpression(stream: TokenStream) -> Expression:
    # TODO multiline
    typeToFunc = {
        START_FUNCTION: parseFunctionCall,
        START_LIST: parseList,
        NUMBER: parseAtom,
        STRING: parseAtom,
        BOOLEAN: parseAtom
    }
    current = stream.peek()
    if current.type not in typeToFunc:
        raise SyntaxError(f'Expected expression: {current}')
    else:
        parsingFunction = typeToFunc[current.type]
        return parsingFunction(stream)


def parseAssignment(stream: TokenStream) -> Assignment:
    pass

def parseFunctionDefinition(Stream: TokenStream) -> FunctionDefinition:
    pass

def parseStatement(stream: TokenStream) -> Statement:
    pass

def parseBody(stream: TokenStream) -> Body:
    pass

def parseTokens(tokens: List[Token]) -> MainBody:
    stream = TokenStream(tokens)
    statements = []
    while not stream.done:
        current = stream.peek()
        if current.type == KEYWORD and current.value == 'return':
            raise SyntaxError('Unexpected identifier: return')
        else:
            parseStatement(stream)

        
        # expect the helper function to advance the stream
        # stream.advance()

