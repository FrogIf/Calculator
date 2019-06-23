package frog.calculator.resolve.dresolver;

import frog.calculator.express.IExpression;
import frog.calculator.express.mid.AddExpression;
import frog.calculator.express.mid.DivExpression;
import frog.calculator.express.mid.MultExpression;
import frog.calculator.express.mid.SubExpression;

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
        resolveResult.setSymbol(String.valueOf(chars[startIndex]));
    }
}
