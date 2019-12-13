parser grammar SubduceParser;

options {
    tokenVocab = 'SubduceLexer';
}

program : statement*;

statement // doesn't include return statements
    : variableAssignment
    | functionDefinition
    | printStatement
    | expression
    ;

variableAssignment : IDENTIFIER EQ expression;

functionDefinition :
    DEF LPAREN IDENTIFIER IDENTIFIER+ RPAREN LBRACE
        statement*
        returnStatement
    RBRACE;

returnStatement : RETURN expression;

printStatement : PRINT expression;

expression
    : lambdaDefinition
    | functionCall
    | IDENTIFIER
    | NUMBER
    | string
    | BOOLEAN
    ;

lambdaDefinition : LPAREN LAM LPAREN IDENTIFIER* RPAREN expression RPAREN;

functionCall : LPAREN expression expression+ RPAREN;

string : QUOTE stringContents* QUOTE;
stringContents
    : NORMAL_CHARACTER
    | ESCAPED_CHARACTER;