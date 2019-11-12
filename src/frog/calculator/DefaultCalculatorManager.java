package frog.calculator;

import frog.calculator.build.DefaultExpressionBuilder;
import frog.calculator.build.IExpressionBuilder;
import frog.calculator.build.resolve.IResolverResult;
import frog.calculator.build.resolve.IResolverResultFactory;
import frog.calculator.connect.DefaultCalculatorSession;
import frog.calculator.connect.ICalculatorSession;
import frog.calculator.express.IExpression;

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
    public IExpressionBuilder createExpressionBuilder(ICalculatorSession session) {
        return new DefaultExpressionBuilder(session);
    }
}
