package frog.calculator.compile.lexical;

import frog.calculator.compile.syntax.ISyntaxNodeBuilder;

public class NamingToken implements IToken {

    private final String word;

    public NamingToken(String word){
        this.word = word;
    }

    @Override
    public ISyntaxNodeBuilder getSyntaxBuilder() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String word() {
        return this.word;
    }
    
}
