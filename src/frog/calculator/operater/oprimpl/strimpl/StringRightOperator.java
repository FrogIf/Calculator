package frog.calculator.operater.oprimpl.strimpl;

import frog.calculator.express.IExpression;
import frog.calculator.express.result.AResultExpression;
import frog.calculator.express.right.RightExpression;
import frog.calculator.operater.IOperator;

public class StringRightOperator implements IOperator {

    @Override
    public AResultExpression operate(IExpression expression) {
        RightExpression rightExpression = (RightExpression) expression;
        AResultExpression leftResult = rightExpression.getLeft().interpret();
        return new StringResultExpression(leftResult.resultValue() + expression.symbol());
    }
}
