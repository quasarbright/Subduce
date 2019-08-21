'''
This module is responsible for converting from a list of tokens to a structured parse tree

Each parsing function expects to be started on the first token associated with its language feature. Except for lambda definitions, which expect to start on the 'lam' after the (

All parsing functions leave the stream on the token after the last token associated with its languange feature. Except for function definitions and bodies, which consume a newline(s) and unindent(s) following the final statement

This module handles indentation. It does this by keeping track of the indentation level associated with a body, and ensuring every statement within that body starts on that indentation level. Within a statement/expression, newlines, indents, and unindents are ignored. Except for function definitions, in which the header must be on one line
'''
from typing import List
from lexer import *
from parseTree import *

WHITESPACE_TYPES = [NEWLINE, INDENT, UNINDENT]

class TokenStream:
    def __init__(self, tokens: List[Token]):
        self.tokens = tokens
        if len(tokens) == 0:
            self.done = True
        else:
            self.done = False
            self.index = 0
            self.current = self.tokens[self.index]
            self.indentationLevel = 0
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
                if self.current.type == INDENT:
                    self.indentationLevel += 1
                elif self.current.type == UNINDENT:
                    self.indentationLevel -= 1
                # should be impossible
                assert self.indentationLevel >= 0
    def skipWhitespace(self) -> None:
        '''advance the stream until it runs out or the current character is not whitespace
        '''
        while not self.done and self.current.type in WHITESPACE_TYPES:
            self.advance()


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
    stream.skipWhitespace()
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
            stream.skipWhitespace()
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
    stream.skipWhitespace()
    if stream.done:
        raise SyntaxError(f'Expected lambda arguments in {typeToChar[START_FUNCTION]}{typeToChar[END_FUNCTION]}')
    current = stream.peek()
    if current.type != START_FUNCTION:
        raise SyntaxError(f'Expected lambda arguments in {typeToChar[START_FUNCTION]}{typeToChar[END_FUNCTION]}: {current}')
    stream.advance()
    stream.skipWhitespace()
    if stream.done:
        raise SyntaxError(f'Expected argument name or {typeToChar[END_FUNCTION]}')
    # get list of argument names
    argumentVars = [] # variable references
    while True:
        current = stream.peek()
        if current.type == END_FUNCTION:
            stream.advance()
            stream.skipWhitespace()
            break
        # make sure it's an identifier
        if current.type != IDENTIFIER:
            raise SyntaxError(f'Expected argument name or {typeToChar[END_FUNCTION]}: {current}')
        argumentReference = VariableReference(current)
        argumentVars.append(argumentReference)
        stream.advance()
        stream.skipWhitespace()
        if stream.done:
            raise SyntaxError(f'Expected argument name or {typeToChar[END_FUNCTION]}')
    if len(argumentVars) == 0:
        raise SyntaxError('Functions must have at least one argument')
    # get return expression
    returnedExpression = parseExpression(stream)
    stream.skipWhitespace()
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
    stream.skipWhitespace()
    if stream.done:
        raise SyntaxError('Expected function')
    current = stream.peek()
    # this might actually be a lambda definition
    if current.value == 'lam':
        return parseLambda(stream)
    # this is a function call, the function is the current expression
    # This will usually be a variable reference to a function, but might not be
    function = parseExpression(stream)
    stream.skipWhitespace()
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
            stream.skipWhitespace()
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
    stream.skipWhitespace()
    if stream.done:
        raise SyntaxError(f'Expected an {typeToChar[EQUALS]}')
    current = stream.peek()
    if current.type != EQUALS:
        raise SyntaxError(f'Expected an {typeToChar[EQUALS]}')
    stream.advance()
    stream.skipWhitespace()
    if stream.done:
        raise SyntaxError('Expected an expression')
    value = parseExpression(stream)
    ans = Assignment(variable, value)
    return ans

def parseFunctionSignature(stream: TokenStream) -> FunctionSignature:
    '''parses from "(" to ")"
    Enforces non-multiline
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
    # skip any additional newlines before first statement
    while not stream.done:
        current = stream.peek()
        if current.type == NEWLINE:
            stream.advance()
        else:
            break
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
    stream.skipWhitespace()
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
    indentationLevel = stream.indentationLevel
    stream.advance()
    if stream.done:
        raise SyntaxError('Expected a variable assignment, function definition, print statement, or return statement')
    current = stream.peek()
    statements = []
    while not stream.done:
        current = stream.peek()
        # handle indentation/whitespace
        if current.type == UNINDENT and stream.indentationLevel == indentationLevel - 1:
            # we unindented out of the body
            stream.advance()
            break
        elif current.type == INDENT:
            # we shouldn't see indents in the body
            raise SyntaxError('Unexpected indent')
        elif current.type in WHITESPACE_TYPES:
            # skip whatever other whitespace we see
            stream.advance()
            continue
        if stream.indentationLevel != indentationLevel:
            raise SyntaxError('Indentation error')
        
        # parse a statement
        # normally, we expect the pattern (statement newline)*
        statements.append(parseStatement(stream))
        if stream.done:
            break
        current = stream.peek()
        if current.type == END_FILE:
            stream.advance()
            break

        lastWasFunctionDefinition = len(statements) > 0 and isinstance(statements[-1], FunctionDefinition)
        if not lastWasFunctionDefinition:
            if current.type != NEWLINE:
                # unless we just parsed a function definition, we should get a newline
                # This is because parsing a function definition consume the newline after it
                # while checking for the closing unindent
                raise SyntaxError(f'Expected newline: {current}')
            else:
                stream.advance() # advance over the newline
    # should be impossible
    assert len(statements) > 0
    ans = Body(statements, indentationLevel)
    return ans

def parseTokens(tokens: List[Token]) -> MainBody:
    # TODO account for multiline statements
    stream = TokenStream(tokens)
    statements = []
    while not stream.done:
        current = stream.peek()
        if current.type == KEYWORD and current.value == 'return':
            raise SyntaxError(f'Unexpected return')
        elif current.type == INDENT:
            raise SyntaxError('Unexpected indent')
        elif current.type == END_FILE:
            break
        elif stream.indentationLevel != 0:
            raise SyntaxError(f'Indentation Error: {current}')
        else:
            statements.append(parseStatement(stream))
    ans = MainBody(statements)
    return ans

'''
maybe keep track of indentation in token stream

here's what I'm thinking now: within each parsing function, just make sure the indentation goes back to zero and doesn't go negative. This means you'll end up consuming the newline-unindent at the end of some expressions. This violates the body parsers' assumptions, so you'll have to account for that
'''
