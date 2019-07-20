package frog.calculator.resolver.resolve.factory;

import frog.calculator.express.IExpression;
import frog.calculator.express.endpoint.EndPointExpression;
import frog.calculator.operator.NoRunOperator;

public class DefaultNumberExpressionFactory implements INumberExpressionFactory {
    @Override
    public IExpression createNumberExpression(String numberStr) {
        return new EndPointExpression(numberStr, NoRunOperator.getInstance());
    }
}
