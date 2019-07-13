package frog.calculator.resolver.resolve;

import frog.calculator.express.IExpression;
import frog.calculator.register.IRegister;
import frog.calculator.resolver.IResolverResult;
import frog.calculator.resolver.build.INumberExpressionFactory;

/**
 * 数字表达式解析器
 */
public class NumberResolver extends AResolver {

    private INumberExpressionFactory numberExpressionFactory;

    public NumberResolver(INumberExpressionFactory numberExpressionFactory, IResolverResult resolveResultPrototype) {
        super(resolveResultPrototype);
        this.numberExpressionFactory = numberExpressionFactory;
    }

    @Override
    protected void resolve(char[] chars, int startIndex, IResolverResult resolveResult) {
        StringBuilder numberBuilder = new StringBuilder();

        boolean hasDot = false;

        for(; startIndex < chars.length; startIndex++){
            char ch = chars[startIndex];

            if(isNumber(ch)){
                numberBuilder.append(ch);
            }else if(isDot(ch)){
                if(hasDot){
                    break;
                }
                numberBuilder.append(ch);
                hasDot = true;
            }else{
                break;
            }
        }

        if(numberBuilder.length() != 0){
            IExpression numberExpression = numberExpressionFactory.createNumberExpression(numberBuilder.toString());
            resolveResult.setEndIndex(startIndex - 1);
            resolveResult.setExpression(numberExpression);
            resolveResult.setSymbol(numberExpression.symbol());
        }
    }

    private boolean isNumber(char ch){
        return ch >= '0' && ch <= '9';
    }

    private boolean isDot(char ch){
        return ch == '.';
    }

    @Override
    public void setRegister(IRegister register) {
        if(this.getNext() != null){
            this.getNext().setRegister(register);
        }
    }
}
