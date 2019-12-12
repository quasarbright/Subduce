grammar Subduce;

program : statement*;

statement // doesn't include return statements
    : variableAssignment
    | functionDefinition
    | printStatement
    ;

variableAssignment : IDENTIFIER '=' expression;

functionDefinition :
    DEF '(' IDENTIFIER IDENTIFIER+ ')' '{'
        statement*
        returnStatement
    '}';

returnStatement : RETURN expression;

printStatement : PRINT expression;

expression
    : lambdaDefinition
    | functionCall
    | IDENTIFIER
    | NUMBER
    | STRING
    | BOOLEAN
    ;

lambdaDefinition : '(' 'lam' '(' IDENTIFIER* ')' expression ')';

functionCall : '(' expression expression+ ')';


// tokens

IDENTIFIER : [a-zA-Z_><=?+] [a-zA-Z0-9\-_><=?+]*;

KEYWORD
    : DEF
    | PRINT
    | RETURN;

DEF : 'def';
PRINT : 'print';
RETURN : 'return';

// atomic types
NUMBER : '-'? DIGIT+ ('.' DIGIT+); //1, -1, 1.0, -1.0, 0.1, -12334.12453253
fragment DIGIT : [0-9];

STRING : '"' CHARACTER* '"';
fragment CHARACTER : NORMAL_CHARACTER | ESCAPED_CHARACTER;
fragment NORMAL_CHARACTER : ~[\r\n\\];
fragment ESCAPED_CHARACTER : '\\' .;

BOOLEAN : 'true' | 'false';

// whitespace
WS : ([\n\t\r ]+ | COMMENT) -> skip;
fragment COMMENT : '#'*? '\n';