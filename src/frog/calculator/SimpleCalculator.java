package frog.calculator;

import frog.calculator.compile.lexical.GeneralLexer;
import frog.calculator.compile.lexical.ILexer;
import frog.calculator.compile.lexical.IToken;
import frog.calculator.compile.lexical.ITokenRepository;
import frog.calculator.compile.lexical.TextScanner;
import frog.calculator.compile.lexical.TokenRepository;
import frog.calculator.compile.lexical.exception.DuplicateTokenException;
import frog.calculator.compile.syntax.GeneralSyntaxTreeBuilder;
import frog.calculator.compile.syntax.ISyntaxNode;
import frog.calculator.compile.syntax.ISyntaxTreeBuilder;
import frog.calculator.connect.ICalculatorSession;
import frog.calculator.exception.CalculatorError;
import frog.calculator.math.number.ComplexNumber;
import frog.calculator.micro.MicroCompileManager;
import frog.calculator.micro.MicroTokenHolder;
import frog.calculator.micro.exec.MicroExecuteContext;
import frog.calculator.platform.ITokenHolder;
import frog.calculator.util.collection.IList;

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
        MicroExecuteContext context = new MicroExecuteContext(session);
        expRoot.execute(context);
        IList<ComplexNumber> result = context.getResult();
        if(result.size() == 1){
            return result.get(0);
        }else{
            return null;
        }
    }
    
    
    
}
