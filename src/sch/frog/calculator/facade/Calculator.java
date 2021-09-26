package sch.frog.calculator.facade;

import sch.frog.calculator.compile.lexical.GeneralLexer;
import sch.frog.calculator.compile.lexical.ILexer;
import sch.frog.calculator.compile.syntax.GeneralSyntaxTreeBuilder;
import sch.frog.calculator.compile.syntax.ISyntaxTreeBuilder;
import sch.frog.calculator.micro.MicroCompileManager;

public class Calculator {

    private final ISyntaxTreeBuilder builder;

    public Calculator(){
        ILexer lexer = GeneralLexer.build(new MicroCompileManager().getTokenFetchers());
        builder = new GeneralSyntaxTreeBuilder(lexer);
    }

    
    
}
