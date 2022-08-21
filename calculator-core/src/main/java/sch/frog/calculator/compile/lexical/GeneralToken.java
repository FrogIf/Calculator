package sch.frog.calculator.compile.lexical;

import sch.frog.calculator.compile.syntax.ISyntaxNodeGenerator;

/**
 * 通用token, 用于平凡token的封装
 */
public class GeneralToken implements IToken {

    private final ISyntaxNodeGenerator builder;

    private final String word;

    public GeneralToken(String word, ISyntaxNodeGenerator builder){
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
