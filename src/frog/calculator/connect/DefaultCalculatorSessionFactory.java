package frog.calculator.connect;

import frog.calculator.resolver.IResolverResultFactory;

public class DefaultCalculatorSessionFactory implements ICalculatorSessionFactory {

    private IResolverResultFactory resolverResultFactory;

    public DefaultCalculatorSessionFactory(IResolverResultFactory resolverResultFactory) {
        this.resolverResultFactory = resolverResultFactory;
    }

    @Override
    public ICalculatorSession createSession() {
        return new DefaultCalculatorSession(this.resolverResultFactory);
    }
}
