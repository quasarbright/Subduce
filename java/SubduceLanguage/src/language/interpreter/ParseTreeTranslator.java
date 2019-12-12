package language.interpreter;

import language.grammar.SubduceParser;
import language.interpreter.expression.Expression;

/**
 * translates a parse tree into an evaluatable expression
 */
public interface ParseTreeTranslator {
    Expression fromParseTree(SubduceParser.ProgramContext ctx);
}
