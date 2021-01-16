package frog.calculator.compile.lexical;

import frog.calculator.compile.IWord;
import frog.calculator.compile.syntax.ISyntaxNodeBuilder;

public interface IToken extends IWord {

    ISyntaxNodeBuilder getSyntaxBuilder();
    
}
