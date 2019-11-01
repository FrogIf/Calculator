package frog.calculator;

import frog.calculator.resolver.IResolverResultFactory;

public interface ICalculatorConfigure {

    IResolverResultFactory getResolverResultFactory();

    IExpressionHolder getExpressionHolder();

    ICommandHolder getCommandHolder();

}
