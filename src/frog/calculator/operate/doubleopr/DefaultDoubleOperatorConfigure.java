package frog.calculator.operate.doubleopr;

import frog.calculator.operate.IOperatorConfigure;
import frog.calculator.operate.IOperatorPool;

public class DefaultDoubleOperatorConfigure implements IOperatorConfigure {

    private IOperatorPool operatorPool = new DefaultDoubleOperatorPool();

    @Override
    public IOperatorPool getIOperatorPool() {
        return operatorPool;
    }

}
