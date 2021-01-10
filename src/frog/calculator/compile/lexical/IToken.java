package frog.calculator.compile.lexical;

import frog.calculator.compile.syntax.ISyntaxNodeBuilder;

public interface IToken {

    ISyntaxNodeBuilder getSyntaxBuilder();

    String word();
    
}
