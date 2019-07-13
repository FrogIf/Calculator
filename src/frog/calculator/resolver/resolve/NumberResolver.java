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

        /*
         * 判断是否为数字:
         *  1. 开始就是数字, 那么是数字
         *  2. 开始是"-", "-"右侧是数字则可能是数字, 需要以下判断:
         *      "-"左侧是否是数字, 如果是, 则不是数字, 否则是
         */

        boolean hasDot = false;
        // TODO 临时修补, 不能使用')'
        if(chars[startIndex] == '-' && (startIndex == 0 || (!isNumber(chars[startIndex - 1]) && chars[startIndex - 1] != ')')) && startIndex + 1 < chars.length && isNumber(chars[startIndex + 1])){
            numberBuilder.append(chars[startIndex]);
            numberBuilder.append(chars[startIndex + 1]);
            startIndex += 2;
        }

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
