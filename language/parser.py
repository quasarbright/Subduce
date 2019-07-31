from lexer import *
from parseTree import *

class TokenStream:
    def __init__(self, tokens):
        self.tokens = tokens
        if len(tokens == 0):
            self.done = True
        else:
            self.done = False
            self.index = 0
            self.current = self.tokens[self.index]
    def peek(self):
        if self.done:
            raise RuntimeError('cant peek done token stream')
        else:
            return self.current

    def advance(self):
        if self.done:
            raise RuntimeError('cant advance done token stream')
        else:
            self.index += 1
            if self.index >= len(self.tokens):
                self.done = True
            else:
                self.current = self.tokens[self.index]


def parseFunctionCall(stream):
    pass

def parseList(stream):
    pass

def parseAtomic(stream):
    pass

def parseExpression(stream):
    pass

def parseStatement(stream):
    pass

def parseBody(stream):
    pass

def parseTokens(tokens) -> MainBody:
    stream = TokenStream(tokens)

    while not stream.done:
        token = stream.peek()
        
        stream.advance()

