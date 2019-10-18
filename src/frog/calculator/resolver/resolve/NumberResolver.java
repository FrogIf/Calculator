package frog.calculator.resolver.resolve;

import frog.calculator.express.IExpression;
import frog.calculator.resolver.IResolverResult;
import frog.calculator.resolver.IResolverResultFactory;
import frog.calculator.resolver.resolve.factory.ISymbolExpressionFactory;
import frog.calculator.util.CharDiscriminator;

/**
 * 数字表达式解析器
 */
public class NumberResolver extends AbstractResolver {

    private ISymbolExpressionFactory numberExpressionFactory;

    public NumberResolver(ISymbolExpressionFactory numberExpressionFactory, IResolverResultFactory resolverResultFactory) {
        super(resolverResultFactory);
        this.numberExpressionFactory = numberExpressionFactory;
    }

    @Override
    protected void resolve(char[] chars, int startIndex, IResolverResult resolveResult) {
        StringBuilder numberBuilder = new StringBuilder();

        for(; startIndex < chars.length; startIndex++){
            char ch = chars[startIndex];

            if(CharDiscriminator.isNumber(ch)){
                numberBuilder.append(ch);
            }else if(isDot(ch)){
                numberBuilder.append(ch);
            }else{
                break;
            }
        }

        if(numberBuilder.length() != 0){
            IExpression numberExpression = numberExpressionFactory.createExpression(numberBuilder.toString());
            resolveResult.setEndIndex(startIndex - 1);
            resolveResult.setExpression(numberExpression);
            resolveResult.setSymbol(numberExpression.symbol());
        }
    }

    private boolean isDot(char ch){
        return ch == '.';
    }

}
