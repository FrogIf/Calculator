package frog.calculator;

import frog.calculator.command.ICommandDetector;
import frog.calculator.command.ICommandHolder;
import frog.calculator.express.IExpressionHolder;
import frog.calculator.resolver.IResolver;
import frog.calculator.resolver.IResolverResultFactory;

public interface ICalculatorComponentFactory {

    IResolverResultFactory createResolverResultFactory();

    ICommandHolder createCommandHolder(ICalculatorManager calculatorManager, ICalculatorConfigure configure);

    ICalculatorManager createCalculatorManager(ICalculatorConfigure configure);

    IResolver createResolver(IExpressionHolder holder, ICalculatorManager manager);

    ICommandDetector createCommandDetector(ICommandHolder holder);

    IExpressionHolder createExpressionHolder();

}
