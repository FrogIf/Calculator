package frog.calculator;

import frog.calculator.resolve.IResolverConfigure;
import frog.calculator.resolve.dresolver.DefaultResolverConfigure;

public class DefaultCalculatorConfigure implements ICalculatorConfigure {

    private IResolverConfigure resolverConfigure = new DefaultResolverConfigure();

    @Override
    public IResolverConfigure getResolverConfigure() {
        return this.resolverConfigure;
    }

}
