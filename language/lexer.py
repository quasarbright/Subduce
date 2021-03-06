'''
The job of the lexer is to tokenize the source code
The lexer takes the source code, and converts it to a list of tokens.
The lexer doesn't build parsing trees.
That is the job of the parser.
'''
from typing import List
import re

# token types
START_FILE = '<start file>'
END_FILE = '<end file>'
NEWLINE = '<newline>'
INDENT = '<indent>'
UNINDENT = '<unindent>'
NUMBER = '<number>'
STRING = '<string>'
BOOLEAN = '<boolean>'
IDENTIFIER = '<identifier>'
KEYWORD = '<keyword>'
EQUALS = '<equals>'
END_SIGNATURE = '<end signature>'
START_FUNCTION = '<start function>'
END_FUNCTION = '<end function>'
START_LIST = '<start list>'
END_LIST = '<end list>'
# utility variables
specialCharacters = '#()[]:"=\n\t '  # can't be in variables
oneLengthTokens = '()[]:=\n'  # characters that are tokens of length 1
charToType = {
    '(': START_FUNCTION,
    ')': END_FUNCTION,
    '[': START_LIST,
    ']': END_LIST,
    ':': END_SIGNATURE,
    '"': STRING,
    '=': EQUALS,
    '\n': NEWLINE,
}
typeToChar = {
    START_FUNCTION: '(',
    END_FUNCTION: ')',
    START_LIST: '[',
    END_LIST: ']',
    END_SIGNATURE: ':',
    STRING: '"',
    EQUALS: '=',
    NEWLINE: '\n'
}
keywords = ['def', 'return', 'print']
whitespace = '\t '

class InputStream:
    def __init__(self, text):
        self.done = False
        self.text = text
        self.index = 0
        self.lineNumber = 1
        self.characterPosition = 1
        self.character = None
        if len(text) > 0:
            self.character = text[0]
        else:
            self.done = True
    
    def peek(self) -> dict:
        '''returns current state without moving to the next character'''
        if self.isDone():
            raise RuntimeError('peeked finished stream')
        return {
            "index": self.index,
            "lineNumber": self.lineNumber,
            "characterPosition": self.characterPosition,
            "character": self.character
        }
    
    def advance(self) -> None:
        '''move to the next character'''
        if not self.hasNext():
            self.done = True
            return None
        self.index += 1
        self.character = self.text[self.index]
        if self.character == '\n':
            self.lineNumber += 1
            self.characterPosition = 0
        else:
            self.characterPosition += 1
    
    def advanceLine(self) -> None:# needs testing
        '''move to next line (advances to newline then stops without passing it)'''
        # character = self.peek()['character']
        # while self.hasNext():
        #     if character == '\n':
        #         return None
        #     character = self.getNext()['character']
        while not self.isDone():
            state = self.peek()
            character = state['character']
            if character == '\n':
                # stop, we're on a newline
                break
            else:
                self.advance()

    def getNext(self) -> dict:
        '''returns state and moves on to next character'''
        state = self.peek()
        self.advance()
        return state

    def hasNext(self) -> bool:
        '''does the stream have a next character?'''
        return self.index < len(self.text) - 1
    
    def isDone(self) -> bool:
        '''is the stream done? (already passed the last character)?'''
        return self.done

class Token:
    def __init__(self, tokenType, lineNumber, startPosition=None, endPosition=None, value=None):
        self.type = tokenType
        self.lineNumber = lineNumber
        self.startPosition = startPosition
        self.endPosition = endPosition
        self.value = value
    def __eq__(self, other: 'Token') -> bool:
        return self.type == other.type and self.lineNumber == other.lineNumber and self.startPosition == other.startPosition and self.endPosition == other.endPosition and self.value == other.value
    
    def __hash__(self):
        return hash(tuple(self.type, self.lineNumber, self.startPosition, self.endPosition, self.value))

    def __str__(self):
        ans = f'{self.type} at {self.lineNumber}'
        if self.startPosition is not None:
            assert self.endPosition is not None
            ans += f':{self.startPosition}-{self.endPosition}'
        if self.value is not None:
            ans += f'. value: {self.value}'
        return ans
    def __repr__(self):
        return f'Token({self.type}, {self.lineNumber}, {self.startPosition}, {self.endPosition}, {self.value})'

def getTokenType(startCharacter: str) -> str:
    '''determine token type based on its first character'''
    assert len(startCharacter) == 1
    if startCharacter in charToType:
        return charToType[startCharacter]
    else:
        # it's either an identifier, keyword or a number
        if startCharacter in '-.0123456789':
            return NUMBER
        else:
            return IDENTIFIER
            # should probably validate that it's what you expect for an identifier and error 
            # otherwise

def tokenizeNumber(stream: InputStream) -> Token:
    '''consume number and return token. May raise syntax error
    alters stream
    '''
    state = stream.peek()
    lineNumber = state['lineNumber']
    startCharacterPosition = state['characterPosition']
    character = state['character']
    valueStr = ''
    length = 0
    while character in '-.0123456789':
        valueStr += character
        length += 1
        stream.advance()
        if stream.isDone():
            break
        state = stream.peek()
        character = state['character']
    endCharacterPosition = startCharacterPosition + length

    validRegex = r'-?\d+\.?\d*|-?\d*\.?\d+'
    validMatch = re.fullmatch(validRegex, valueStr)
    if validMatch is None:
        raise SyntaxError('invalid number syntax') # TODO change
    else:
        if '.' in valueStr:
            value = float(valueStr)
        else:
            value = int(valueStr)
    return Token(NUMBER, lineNumber, startCharacterPosition, endCharacterPosition, value)
        
def tokenizeString(stream: InputStream) -> Token:
    '''consume number and return token. May raise syntax error
    alters stream
    '''
    state = stream.peek()
    stream.advance()
    character = state['character']
    if character != '"':
        raise SyntaxError('expected "') # TODO change
    lineNumber = state['lineNumber']
    startPosition = state['characterPosition']
    valueStr = '' # don't want " in value
    if stream.isDone():
        raise SyntaxError('Unexpected <end file> while parsing') # TODO change
    escaping = False
    stringEnded = False
    state = stream.peek()
    character = state['character']
    length = 1 # 1 from the quote
    while True:
        if character == '\n':
            raise SyntaxError('Unexpected token <newline>') # TODO change
        if escaping:
            if character == 'n':
                # newline
                valueStr += '\n'
            elif character == 't':
                # tab
                valueStr += '\t'
            elif character == 'r':
                # carriage return
                valueStr += '\r'
            elif character == 'a':
                # bell
                valueStr += '\a'
            elif character == 'b':
                # backspace
                valueStr += '\b'
            elif character == '"':
                # quote
                valueStr += '"'
            elif character == '\\':
                # backslash
                valueStr += '\\'
            else:
                # dummy escape. Just do the character straight up
                valueStr += character
            length += 1
            escaping = False
        elif character == '\\':
            # escape the next character
            escaping = True
            length += 1
        elif character == '"':
            # string is closing
            stringEnded = True
            length += 1
            stream.advance()
            break
        elif character == '\n':
            raise SyntaxError(f'Unexpected token <newline> at {state["lineNumber"]}:{state["characterPosition"]}')
        else:
            # normal character
            valueStr += character
            length += 1
        stream.advance()
        if not stream.isDone():
            state = stream.peek()
            character = state['character']
        else:
            break
    if not stringEnded:
        # we hit the end of the file and the string didn't end
        raise SyntaxError('Unexpected <end file> while parsing') # TODO change
    endPosition = startPosition + length
    return Token(STRING, lineNumber, startPosition, endPosition, valueStr)

def tokenizeIdentifier(stream: InputStream) -> Token:
    '''consume number and return token. May raise syntax error
    alters stream
    may return keyword token or identifier token
    '''
    state = stream.peek()
    character = state['character']
    lineNumber = state['lineNumber']
    startPosition = state['characterPosition']
    name = ''
    length = 0
    while character not in specialCharacters:
        name += character
        length += 1
        stream.advance()
        if stream.isDone():
            ranOut = True
            break
        state = stream.peek()
        character = state['character']
    endPosition = startPosition + length
    if name == 'true':
        return Token(BOOLEAN, lineNumber, startPosition, endPosition, True)
    elif name == 'false':
        return Token(BOOLEAN, lineNumber, startPosition, endPosition, False)
    if name in keywords:
        return Token(KEYWORD, lineNumber, startPosition, endPosition, name)
    else:
        return Token(IDENTIFIER, lineNumber, startPosition, endPosition, name)

def getIndentationLevels(text: str) -> List[int]:
    '''returns the indentation levels of the lines of
    the file
    '''
    lines = text.splitlines()
    indentLevels = []
    for line in lines:
        level = 0
        for character in line:
            if character == '\t':
                level += 1
            else:
                break
        indentLevels.append(level)
    return indentLevels

def tokenize(text: str) -> List[Token]:
    stream = InputStream(text)
    lineNumber = 1
    indentations = [0] # list of indentation levels by line
    # excludes whitespace lines
    indentationLevel = 0
    inNewLine = True
    tokens = [Token(START_FILE, 1)]
    while not stream.isDone():
        state = stream.peek()
        character = state['character']
        lineNumber = state['lineNumber']
        characterPosition = state['characterPosition']
        # handle whitespace
        # tab indentation
        if character == '\t':
            indentationLevel += 1
            stream.advance()
            continue
        if character == ' ':
            # ignore spaces
            stream.advance()
            continue
        elif character == '#':
            # if comment, go to next line
            stream.advanceLine()
            continue
        elif character == '\n':
            # new line
            tokens.append(Token(NEWLINE, lineNumber))
            inNewLine = True
            indentationLevel = 0
            stream.advance()
            continue
        
        # now it's a statement for sure
        # handle indentation
        inNewLine = False
        previousIndentationLevel = indentations[-1]
        indentations.append(indentationLevel)
        if indentationLevel > previousIndentationLevel:
            # indentation level increased
            difference = indentationLevel - previousIndentationLevel
            for level in range(difference):
                tokens.append(Token(INDENT, lineNumber))
        elif indentationLevel < previousIndentationLevel:
            difference = previousIndentationLevel - indentationLevel
            for level in range(difference):
                tokens.append(Token(UNINDENT, lineNumber))
        
        # handle statements and expressions
        if character in oneLengthTokens:
            tokenType = getTokenType(character)
            tokens.append(Token(tokenType, lineNumber, characterPosition, characterPosition+1))
            stream.advance()
            continue
        else:
            tokenType = getTokenType(character)
            if tokenType == STRING:
                tokens.append(tokenizeString(stream))
                continue
            elif tokenType == NUMBER:
                tokens.append(tokenizeNumber(stream))
                continue
            elif tokenType == IDENTIFIER:
                tokens.append(tokenizeIdentifier(stream))
                continue
        stream.advance()
        continue
    tokens.append(Token(END_FILE, lineNumber+1))
    return tokens

def main():
    import sys
    if len(sys.argv) < 2:
        sys.exit('must provide file path to lexer')
    else:
        path = sys.argv[1]
        with open(path, 'r') as f:
            code = f.read()
            tokens = tokenize(code)
            strTokens = [str(token) for token in tokens]
            print('\n'.join(strTokens))

if __name__ == '__main__':
    main()
