package frog.calculator.resolve.resolver;

import frog.calculator.express.context.IExpressContext;
import frog.calculator.express.end.NumberExpression;

public class DefaultNumberExpressionFactory implements INumberExpressionFactory {

    private IExpressContext context;

    private INumberOperatorFactory numberOperatorFactory;

    public DefaultNumberExpressionFactory(IExpressContext context, INumberOperatorFactory numberOperatorFactory) {
        this.context = context;
        this.numberOperatorFactory = numberOperatorFactory;
    }

    @Override
    public NumberExpression createNumberExpression() {
        return new NumberExpression(numberOperatorFactory.createNumberOperator(), context);
    }
}
