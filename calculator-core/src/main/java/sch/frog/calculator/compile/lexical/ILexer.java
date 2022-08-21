package sch.frog.calculator.compile.lexical;

import sch.frog.calculator.compile.lexical.exception.UnrecognizedTokenException;
import sch.frog.calculator.util.collection.IList;

/**
 * 词法解析器
 */
public interface ILexer {

    IList<IToken> tokenization(IScanner scanner) throws UnrecognizedTokenException;
    
}
