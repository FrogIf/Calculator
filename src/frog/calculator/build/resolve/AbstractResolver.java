package frog.calculator.build.resolve;

import frog.calculator.ICalculatorManager;

public abstract class AbstractResolver implements IResolver {

    protected ICalculatorManager manager;

    public AbstractResolver(ICalculatorManager manager) {
        if(manager == null){
            throw new IllegalArgumentException("calculator manager is null : " + this.getClass().getName());
        }
        this.manager = manager;
    }
}
