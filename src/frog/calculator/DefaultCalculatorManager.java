package frog.calculator;

import frog.calculator.connect.DefaultCalculatorSession;
import frog.calculator.connect.ICalculatorSession;
import frog.calculator.express.DefaultExpressionContext;
import frog.calculator.express.IExpression;
import frog.calculator.express.IExpressionContext;
import frog.calculator.resolver.IResolverResult;
import frog.calculator.resolver.IResolverResultFactory;

public class DefaultCalculatorManager implements ICalculatorManager {

    private final IResolverResultFactory resolverResultFactory;

    public DefaultCalculatorManager(ICalculatorConfigure configure) {
        this.resolverResultFactory = configure.getComponentFactory().createResolverResultFactory();
    }

    @Override
    public IResolverResult createResolverResult(IExpression expression) {
        return resolverResultFactory.createResolverResultBean(expression);
    }

    @Override
    public ICalculatorSession createCalculatorSession() {
        return new DefaultCalculatorSession(this);
    }

    @Override
    public IExpressionContext createExpressionContext(ICalculatorSession session) {
        return new DefaultExpressionContext(session);
    }
}
