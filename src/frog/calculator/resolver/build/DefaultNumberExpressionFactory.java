package frog.calculator.resolver.build;

import frog.calculator.express.IExpression;
import frog.calculator.express.endpoint.EndPointExpression;
import frog.calculator.operater.NoRunOperator;

public class DefaultNumberExpressionFactory implements INumberExpressionFactory {
    @Override
    public IExpression createNumberExpression(String numberStr) {
        return new EndPointExpression(numberStr, NoRunOperator.getInstance());
    }
}
