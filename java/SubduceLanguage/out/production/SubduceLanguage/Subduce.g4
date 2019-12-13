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

lambdaDefinition : '(' LAM '(' IDENTIFIER* ')' expression ')';

functionCall : '(' expression expression+ ')';


LAM : 'lam';
DEF : 'def';
PRINT : 'print';
RETURN : 'return';

NUMBER : '-'? DIGIT+ ('.' DIGIT+)?; //1, -1, 1.0, -1.0, 0.1, -12334.12453253
fragment DIGIT : [0-9];

STRING : '"' CHARACTER* '"';
fragment CHARACTER : NORMAL_CHARACTER | ESCAPED_CHARACTER;
fragment NORMAL_CHARACTER : ~[\r\n\\"];
fragment ESCAPED_CHARACTER : '\\' ~[\n\r];

BOOLEAN : 'true' | 'false';

IDENTIFIER : '=' | IDENTIFIER_START_CHARACTER IDENTIFIER_REST_CHARACTER*;
fragment IDENTIFIER_START_CHARACTER : [a-zA-Z\-_=><?+*];
fragment IDENTIFIER_REST_CHARACTER : IDENTIFIER_START_CHARACTER | [0-9];

WS : (WS_C+ | COMMENT | MULTILINE_COMMENT) -> skip;
fragment WS_C : [\n\t\r ];
fragment COMMENT : '//' ~[\n\r]*? '\n';
fragment MULTILINE_COMMENT : '/*' .*? '*/';