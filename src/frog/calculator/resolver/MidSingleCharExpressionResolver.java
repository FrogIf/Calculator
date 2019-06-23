package frog.calculator.resolver;

import frog.calculator.expression.IExpression;
import frog.calculator.expression.mid.AddExpression;
import frog.calculator.expression.mid.DivExpression;
import frog.calculator.expression.mid.MultExpression;
import frog.calculator.expression.mid.SubExpression;

public class MidSingleCharExpressionResolver extends AResolver {

    private IExpression getExpression(char symbol){
        IExpression exp = null;
        switch (symbol){
            case '+':
                exp = new AddExpression();
                break;
            case '-':
                exp = new SubExpression();
                break;
            case '*':
                exp = new MultExpression();
                break;
            case '/':
                exp = new DivExpression();
                break;
        }
        return exp;
    }

    @Override
    protected void resolve(char[] chars, int startIndex, AResolveResult resolveResult) {
        IExpression expression = this.getExpression(chars[startIndex]);
        resolveResult.setExpression(expression);
        resolveResult.setEndIndex(startIndex);
    }
}
