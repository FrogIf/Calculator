package frog.calculator;

import frog.calculator.common.exec.GeneralExecuteContext;
import frog.calculator.compile.lexical.GeneralLexer;
import frog.calculator.compile.lexical.ILexer;
import frog.calculator.compile.lexical.IToken;
import frog.calculator.compile.lexical.ITokenRepository;
import frog.calculator.compile.lexical.TextScanner;
import frog.calculator.compile.lexical.TokenRepository;
import frog.calculator.compile.lexical.exception.DuplicateTokenException;
import frog.calculator.compile.semantic.IResult;
import frog.calculator.compile.semantic.IValue;
import frog.calculator.compile.semantic.IResult.ResultType;
import frog.calculator.compile.syntax.GeneralSyntaxTreeBuilder;
import frog.calculator.compile.syntax.ISyntaxNode;
import frog.calculator.compile.syntax.ISyntaxTreeBuilder;
import frog.calculator.connect.ICalculatorSession;
import frog.calculator.exception.CalculatorError;
import frog.calculator.math.number.ComplexNumber;
import frog.calculator.micro.MicroCompileManager;
import frog.calculator.micro.MicroTokenHolder;
import frog.calculator.micro.MicroUtil;
import frog.calculator.platform.ITokenHolder;
import frog.test.util.DebugUtil;

public class SimpleCalculator implements ICalculator<ComplexNumber> {

    private final ISyntaxTreeBuilder builder;

    public SimpleCalculator(){
        ITokenHolder holder = new MicroTokenHolder();
        ITokenRepository tokenRespository = new TokenRepository();
        try {
            for(IToken t : holder.getTokens()){
                tokenRespository.insert(t);
            }
        } catch (DuplicateTokenException e) {
            throw new CalculatorError(e.getMessage());
        }    
        ILexer lexer = new GeneralLexer(tokenRespository, new MicroCompileManager());
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
