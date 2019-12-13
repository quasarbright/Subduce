lexer grammar SubduceLexer;

EQ : '=';
LPAREN : '(';
RPAREN : ')';
LBRACE : '{';
RBRACE : '}';


LAM : 'lam';
DEF : 'def';
PRINT : 'print';
RETURN : 'return';

NUMBER : '-'? DIGIT+ ('.' DIGIT+)?; //1, -1, 1.0, -1.0, 0.1, -12334.12453253
fragment DIGIT : [0-9];

//STRING : '"' CHARACTER* '"';
//fragment CHARACTER : NORMAL_CHARACTER | ESCAPED_CHARACTER;
//fragment NORMAL_CHARACTER : ~[\r\n\\"];
//fragment ESCAPED_CHARACTER : '\\' ~[\n\r];

BOOLEAN : 'true' | 'false';

IDENTIFIER : IDENTIFIER_START_CHARACTER IDENTIFIER_REST_CHARACTER*;
fragment IDENTIFIER_START_CHARACTER : [a-zA-Z\-_=><?+*];
fragment IDENTIFIER_REST_CHARACTER : IDENTIFIER_START_CHARACTER | [0-9];

WS : (WS_C+ | COMMENT | MULTILINE_COMMENT) -> skip;
fragment WS_C : [\n\t\r ];
fragment COMMENT : '//' ~[\n\r]*? '\n';
fragment MULTILINE_COMMENT : '/*' .*? '*/';

QUOTE: '"' -> pushMode(IN_STRING);

mode IN_STRING;
NORMAL_CHARACTER : ~[\r\n\\"];
ESCAPED_CHARACTER : '\\' ~[\n\r];
QUOTE_IN_STRING: '"' -> type(QUOTE), popMode;
