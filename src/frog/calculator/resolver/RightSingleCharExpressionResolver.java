package frog.calculator.resolver;

import frog.calculator.expression.IExpression;
import frog.calculator.expression.mid.AddExpression;

public class RightSingleCharExpressionResolver extends AResolver {

    @Override
    protected void resolve(char[] chars, int startIndex, AResolveResult resolveResult) {
        IExpression expression = this.getExpression(chars[startIndex]);
        resolveResult.setEndIndex(startIndex);
        resolveResult.setExpression(expression);
    }

    private IExpression getExpression(char symbol){
        IExpression exp = null;
        switch (symbol){
            case '!':
                exp = new AddExpression();
                break;
        }
        return exp;
    }
}
