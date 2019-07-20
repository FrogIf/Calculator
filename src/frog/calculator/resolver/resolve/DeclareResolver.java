package frog.calculator.resolver.resolve;

import frog.calculator.express.IExpression;
import frog.calculator.resolver.ExpressionType;
import frog.calculator.resolver.IResolverResult;
import frog.calculator.resolver.IResolverResultFactory;

public class DeclareResolver extends AbstractResolver {

    private IExpression declare;

    private char[] declareSymbol;

    public DeclareResolver(IResolverResultFactory resolverResultFactory, IExpression declare) {
        super(resolverResultFactory);
        if(declare == null || declare.symbol() == null){
            throw new IllegalArgumentException("lambda declare symbol is undefined.");
        }
        this.declare = declare;
        declareSymbol = declare.symbol().toCharArray();
    }

    @Override
    protected void resolve(char[] chars, int startIndex, IResolverResult resolveResult) {
        boolean isDeclare = true;
        int len = declareSymbol.length;
        if(chars.length - startIndex >= len){
            for(int i = 0; i < len; i++){
                if(chars[i] != declareSymbol[i]){
                    isDeclare = false;
                    break;
                }
            }
        }
        if(isDeclare){
            resolveResult.setExpression(declare);
            resolveResult.setEndIndex(startIndex + len - 1);
            resolveResult.setType(ExpressionType.DECLARE);
        }
    }
}
