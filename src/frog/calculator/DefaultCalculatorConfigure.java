package frog.calculator;

import frog.calculator.operate.IOperatorConfigure;
import frog.calculator.operate.doubleopr.DefaultDoubleOperatorConfigure;
import frog.calculator.resolve.dresolver.DefaultResolverConfigure;
import frog.calculator.resolve.IResolverConfigure;

public class DefaultCalculatorConfigure implements ICalculatorConfigure {

    private IResolverConfigure resolverConfigure = new DefaultResolverConfigure();

    private IOperatorConfigure operatorConfigure = new DefaultDoubleOperatorConfigure();

    @Override
    public IResolverConfigure getResolverConfigure() {
        return this.resolverConfigure;
    }

    @Override
    public IOperatorConfigure getOperatorConfigure() {
        return this.operatorConfigure;
    }
}
