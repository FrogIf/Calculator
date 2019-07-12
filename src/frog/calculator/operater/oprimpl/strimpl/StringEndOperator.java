package frog.calculator.operater.oprimpl.strimpl;

import frog.calculator.express.IExpression;
import frog.calculator.express.end.EndExpression;
import frog.calculator.express.result.AResultExpression;
import frog.calculator.operater.IOperator;

public class StringEndOperator implements IOperator {

    @Override
    public AResultExpression operate(IExpression expression) {
        EndExpression endExpression = (EndExpression) expression;
        return new StringResultExpression(endExpression.symbol());
    }

}
