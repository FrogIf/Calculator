package frog.calculator.compile.lexical;

import frog.calculator.compile.syntax.ISyntaxNodeGenerator;

/**
 * 公共的token, 用于平凡token的封装
 */
public class CommonToken implements IToken {

    private final ISyntaxNodeGenerator builder;

    private final String word;

    public CommonToken(String word, ISyntaxNodeGenerator builder){
        this.word = word;
        this.builder = builder;
    }

    @Override
    public ISyntaxNodeGenerator getSyntaxNodeGenerator() {
        return this.builder;
    }

    @Override
    public String word() {
        return this.word;
    }
    
}
