package frog.calculator;

import frog.calculator.connect.DefaultCalculatorSession;
import frog.calculator.connect.ICalculatorSession;
import frog.calculator.express.IExpression;
import frog.calculator.resolver.IResolverResult;
import frog.calculator.resolver.IResolverResultFactory;

public class DefaultCalculatorManager implements ICalculatorManager {

    private final IResolverResultFactory resolverResultFactory;

    public DefaultCalculatorManager(ICalculatorConfigure configure) {
        this.resolverResultFactory = configure.getResolverResultFactory();
    }

    @Override
    public IResolverResult createResolverResult(IExpression expression) {
        return resolverResultFactory.createResolverResultBean(expression);
    }

    @Override
    public ICalculatorSession createCalculatorSession() {
        return new DefaultCalculatorSession(this);
    }
}
