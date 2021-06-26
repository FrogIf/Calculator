package frog.calculator;

import frog.calculator.common.exec.GeneralExecuteContext;
import frog.calculator.compile.lexical.GeneralLexer;
import frog.calculator.compile.lexical.ILexer;
import frog.calculator.compile.lexical.TextScanner;
import frog.calculator.compile.semantic.IResult;
import frog.calculator.compile.semantic.IValue;
import frog.calculator.compile.semantic.IResult.ResultType;
import frog.calculator.compile.syntax.GeneralSyntaxTreeBuilder;
import frog.calculator.compile.syntax.ISyntaxNode;
import frog.calculator.compile.syntax.ISyntaxTreeBuilder;
import frog.calculator.connect.ICalculatorSession;
import frog.calculator.math.number.ComplexNumber;
import frog.calculator.micro.MicroCompileManager;
import frog.calculator.micro.MicroUtil;

public class SimpleCalculator implements ICalculator<ComplexNumber> {

    private final ISyntaxTreeBuilder builder;

    public SimpleCalculator(){
        ILexer lexer = GeneralLexer.build(new MicroCompileManager().getTokenFetchers());
        builder = new GeneralSyntaxTreeBuilder(lexer);
    }

    public ComplexNumber calculate(String expression, ICalculatorSession session) {
        ISyntaxNode expRoot = this.builder.build(new TextScanner(expression));
        // System.out.println(DebugUtil.getSyntaxTree(expRoot));
        GeneralExecuteContext context = new GeneralExecuteContext(session);
        IResult result = expRoot.execute(context);
        if(result.getResultType() == ResultType.VALUE){
            IValue value = result.getValue();
            return MicroUtil.getNumber(value, context);
        }else{
            return null;
        }
    }
    
    
    
}
