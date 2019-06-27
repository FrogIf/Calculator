package frog.calculator.resolve.dresolver;

import frog.calculator.express.IExpression;
import frog.calculator.express.right.FactorialExpression;
import frog.calculator.express.right.PercentExpression;

public class RightSingleCharExpressionResolver extends AResolver {

    @Override
    protected void resolve(char[] chars, int startIndex, AResolveResult resolveResult) {
        IExpression expression = this.getExpression(chars[startIndex]);
        resolveResult.setEndIndex(startIndex);
        resolveResult.setExpression(expression);
        resolveResult.setSymbol(String.valueOf(chars[startIndex]));
    }

    private IExpression getExpression(char symbol){
        IExpression exp = null;
        switch (symbol){
            case '!':
                exp = new FactorialExpression();
                break;
            case '%':
                exp = new PercentExpression();
                break;
            default:
                break;
        }
        return exp;
    }
}
