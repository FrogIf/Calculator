package frog.calculator.connect;

import frog.calculator.register.IRegister;
import frog.calculator.register.TreeRegister;
import frog.calculator.resolver.IResolver;
import frog.calculator.resolver.IResolverResultFactory;
import frog.calculator.resolver.resolve.SymbolResolver;

public class DefaultCalculatorSession implements ICalculatorSession {

    private IResolver resolver;

    private TreeRegister register = new TreeRegister();

    public DefaultCalculatorSession(IResolverResultFactory factory) {
        this.resolver = new SymbolResolver(factory, register);
    }

    @Override
    public IRegister getRegister(){
        return this.register;
    }

    @Override
    public IResolver getSessionResolver() {
        return this.resolver;
    }
}
