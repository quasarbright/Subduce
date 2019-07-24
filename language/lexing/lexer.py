'''
The job of the lexer is to tokenize everything
This doesn't include building parsing trees.
That is the job of the parser
'''
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
        self.index += 1
        self.character = text[index]
        if character == '\n':
            self.lineNumber += 1
            self.characterPosition = 0
        else:
            self.characterPosition += 1
        
    def getNext(self) -> dict:
        '''returns state and moves on to next character'''
        if not self.hasNext():
            raise RuntimeError('no next')
        state = self.peek()
        self.advance()
        return state
    def hasNext(self) -> bool:
        '''does the stream have a next character?'''
        return self.index < len(self.text) - 1

class Token:
    def __init__(self, type, lineNumber, startPosition, endPosition, value=None):
        self.type = type
        self.lineNumber = lineNumber
        self.startPosition = startPosition
        self.endPosition = endPosition
        self.value = value
    def __eq__(self, other: Token) -> bool:
        return vars(self) == vars(other)
