'''
The job of the lexer is to tokenize everything
This doesn't include building parsing trees.
That is the job of the parser
'''
from typing import List
import re

specialCharacters = '#()[]:"=\n\t '  # can't be in variables
oneLengthTokens = '()[]:=\n'  # characters that are tokens of length 1
keywords = ['def', 'return']
whitespace = '\t '

class InputStream:
    def __init__(self, text):
        self.text = text
        self.index = 0
        self.lineNumber = 1
        self.characterPosition = 1
        self.character = None
        if len(text) > 0:
            self.character = text[0]
    def peek(self) -> dict:
        '''returns state without moving to the next character'''
        return {
            "index": self.index,
            "lineNumber": self.lineNumber,
            "characterPosition": self.characterPosition,
            "character": self.character
        }
    def advance(self) -> None:
        '''move to the next character'''
        if not self.hasNext():
            raise RuntimeError('no next')
        self.index += 1
        self.character = self.text[self.index]
        if self.character == '\n':
            self.lineNumber += 1
            self.characterPosition = 0
        else:
            self.characterPosition += 1
    
    def advanceLine(self) -> None:# needs testing
        '''move to next line (advances to newline then stops without passing it)'''
        character = self.peek()['character']
        while self.hasNext():
            if character == '\n':
                return None
            character = self.getNext()['character']
        return None
        
    def getNext(self) -> dict:
        '''returns state and moves on to next character'''
        state = self.peek()
        self.advance()
        return state
    def hasNext(self) -> bool:
        '''does the stream have a next character?'''
        return self.index < len(self.text) - 1

class Token:
    def __init__(self, tokenType, lineNumber, startPosition=None, endPosition=None, value=None):
        self.type = tokenType
        self.lineNumber = lineNumber
        self.startPosition = startPosition
        self.endPosition = endPosition
        self.value = value
    def __eq__(self, other: 'Token') -> bool:
        return self.type == other.type and self.lineNumber == other.lineNumber and self.startPosition == other.startPosition and self.endPosition == other.endPosition and self.value == other.value


def getTokenType(startCharacter: str) -> str:
    '''determine token type based on its first character'''
    assert len(startCharacter) == 1
    charDict = {
        '(': '<start function>',
        ')': '<end function>',
        '[': '<start list>',
        ']': '<end list>',
        ':': '<end signature>',
        '"': '<string>',
        '=': '<equals>',
        '\n': '<newline>',
    }
    if startCharacter in charDict:
        return charDict[startCharacter]
    else:
        # it's either an identifier, keyword or a number
        if startCharacter in '-.0123456789':
            return '<number>'
        else:
            return '<identifier>'
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
    while character in '-.0123456789':
        valueStr += character
        if not stream.hasNext():
            break
        stream.advance()
        state = stream.peek()
        character = state['character']
    endCharacterPosition = state['characterPosition'] + 1

    validRegex = r'-?\d+\.?\d*|-?\d*\.?\d+'
    validMatch = re.fullmatch(validRegex, valueStr)
    if validMatch is None:
        raise SyntaxError('invalid number syntax') # TODO change
    else:
        if '.' in valueStr:
            value = float(valueStr)
        else:
            value = int(valueStr)
    return Token('<number>', lineNumber, startCharacterPosition, endCharacterPosition, value)
    ### left off here about to process the number
    # maybe you want to do this while consuming so you can get character-perfect
    # syntax error location. But that's messy. I'm thinking just throw it at the
    # beginning of the number and say syntax error. But that is vague
        
        


def tokenize(text: str) -> List[Token]:
    stream = InputStream(text)
    tokens = [Token('<start file>', 1)]
    while stream.hasNext():
        state = stream.peek()
        character = state['character']
        lineNumber = state['lineNumber']
        characterPosition = state['characterPosition']
        # handle whitespace
        if character in whitespace:
            # skip whitespace
            stream.advance()
            continue
        elif character == '#':
            # if comment, go to next line
            stream.advanceLine()
        elif character in oneLengthTokens:
            tokenType = character
            tokens.append(Token(tokenType, lineNumber, characterPosition, characterPosition+1))
        else:
            tokenType = getTokenType(character)
            if tokenType == '<string>':
                tokens.append(tokenizeString(stream))
            elif tokenType == '<number>':
                tokens.append(tokenizeNumber(stream))
            elif tokenType == '<identifier>':
                tokens.append(tokenizeIdentifier(stream))
        if stream.hasNext():
            stream.advance()
        else:
            break
    tokens.append(Token('<end file>', lineNumber+1))
    return tokens
