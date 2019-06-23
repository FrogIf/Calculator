package frog.calculator;

import frog.calculator.operate.IOperatorConfigure;
import frog.calculator.resolve.IResolverConfigure;

public interface ICalculatorConfigure {

    IResolverConfigure getResolverConfigure();

    IOperatorConfigure getOperatorConfigure();

}
