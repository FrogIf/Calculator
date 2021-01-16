package frog.calculator.compile.lexical;

import frog.calculator.compile.syntax.ISyntaxNodeBuilder;

public class CommonToken implements IToken {

    private final ISyntaxNodeBuilder builder;

    private final String word;

    public CommonToken(String word, ISyntaxNodeBuilder builder){
        this.word = word;
        this.builder = builder;
    }

    @Override
    public ISyntaxNodeBuilder getSyntaxBuilder() {
        return this.builder;
    }

    @Override
    public String word() {
        return this.word;
    }
    
}
