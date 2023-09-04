package io.github.frogif.calculator.compile.lexical;

import io.github.frogif.calculator.compile.lexical.exception.UnrecognizedTokenException;
import io.github.frogif.calculator.util.collection.IList;

/**
 * 词法解析器
 */
public interface ILexer {

    IList<IToken> tokenization(IScanner scanner) throws UnrecognizedTokenException;
    
}
