package frog.calculator.resolve.resolver;

import frog.calculator.express.end.NumberExpression;
import frog.calculator.register.IRegister;
import frog.calculator.resolve.IResolveResult;

/**
 * 数字表达式解析器
 */
public class NumberResolver extends AResolver{

    private INumberExpressionFactory numberOperatorFactory;

    public NumberResolver(IResolveResultFactory resolveResultFactory, INumberExpressionFactory numberOperatorFactory) {
        super(resolveResultFactory);
        this.numberOperatorFactory = numberOperatorFactory;
    }

    @Override
    protected void resolve(char[] chars, int startIndex, IResolveResult resolveResult) {
        NumberExpression numberExpression = numberOperatorFactory.createNumberExpression();

        boolean hasDot = false;
        if(chars[startIndex] == '-' && (startIndex == 0 || !isNumber(chars[startIndex - 1])) && startIndex + 1 < chars.length && isNumber(chars[startIndex + 1])){
            numberExpression.assemble(chars[startIndex]);
            startIndex++;
        }

        for(; startIndex < chars.length; startIndex++){
            char ch = chars[startIndex];

            if(isNumber(ch)){
                numberExpression.assemble(ch);
            }else if(isDot(ch)){
                if(hasDot){
                    break;
                }
                numberExpression.assemble(ch);
                hasDot = true;
            }else{
                break;
            }
        }

        if(!numberExpression.isEmpty()){
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
