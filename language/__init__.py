import lexer
import parse
import interpreter
def runCode(code: str):
    tokens = lexer.tokenize(code)
    parseTree = parse.parseTokens(tokens)
    interpreter.runMainBody(parseTree)
