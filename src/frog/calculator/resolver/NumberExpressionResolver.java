package frog.calculator.resolver;

import frog.calculator.expression.end.NumberExpression;

/**
 * 数字表达式解析器
 */
public class NumberExpressionResolver extends AResolver{

    @Override
    protected void resolve(char[] chars, int startIndex, AResolveResult resolveResult) {
        NumberExpression numberExpression = new NumberExpression();

        boolean hasDot = false;

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
        }
    }

    private boolean isNumber(char ch){
        return ch >= '0' && ch <= '9';
    }

    private boolean isDot(char ch){
        return ch == '.';
    }

}
