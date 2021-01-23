package frog.calculator.compile.lexical;

import frog.calculator.compile.IWord;
import frog.calculator.compile.syntax.ISyntaxNodeGenerator;

public interface IToken extends IWord {

    ISyntaxNodeGenerator getSyntaxNodeGenerator();
    
}
