package frog.calculator.operater.oprimpl.strimpl;

import frog.calculator.express.IExpression;
import frog.calculator.express.mid.MidExpression;
import frog.calculator.express.result.AResultExpression;
import frog.calculator.operater.IOperator;

public class StringMidOperator implements IOperator {
    @Override
    public AResultExpression operate(IExpression expression) {
        MidExpression midExpression = (MidExpression) expression;
        AResultExpression leftResult = midExpression.getLeft().interpret();
        AResultExpression rightResult = midExpression.getRight().interpret();
        return new StringResultExpression(leftResult.resultValue() + expression.symbol() + rightResult.resultValue());
    }
}
