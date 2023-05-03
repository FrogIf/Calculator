package sch.frog.calculator.compile.lexical;

import sch.frog.calculator.compile.syntax.ISyntaxNodeGenerator;

/**
 * 通用token, 用于平凡token的封装
 */
public class GeneralToken implements IToken {

    private final ISyntaxNodeGenerator builder;

    private final String word;

    private final int position;

    public GeneralToken(String word, ISyntaxNodeGenerator builder, int position){
        this.word = word;
        this.builder = builder;
        this.position = position;
    }

    @Override
    public ISyntaxNodeGenerator getSyntaxNodeGenerator() {
        return this.builder;
    }

    @Override
    public int position() {
        return this.position;
    }

    @Override
    public String word() {
        return this.word;
    }
    
}
