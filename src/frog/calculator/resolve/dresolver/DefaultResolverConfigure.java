package frog.calculator.resolve.dresolver;

import frog.calculator.resolve.IResolver;
import frog.calculator.resolve.IResolverConfigure;

public class DefaultResolverConfigure implements IResolverConfigure {

    private IResolver resolver;

    public DefaultResolverConfigure() {
        NumberResolver numberResolver = new NumberResolver();

        SymbolResolver symbolResolver = new SymbolResolver();
        numberResolver.setNextResolver(symbolResolver);

        this.resolver = numberResolver;
    }

    @Override
    public IResolver getResolver() {
        return this.resolver;
    }
}
