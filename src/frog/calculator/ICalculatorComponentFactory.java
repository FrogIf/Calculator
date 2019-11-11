package frog.calculator;

import frog.calculator.build.command.ICommandDetector;
import frog.calculator.build.command.ICommandHolder;
import frog.calculator.express.IExpressionHolder;
import frog.calculator.build.resolve.IResolver;
import frog.calculator.build.resolve.IResolverResultFactory;

public interface ICalculatorComponentFactory {

    IResolverResultFactory createResolverResultFactory();

    ICommandHolder createCommandHolder(ICalculatorManager calculatorManager, ICalculatorConfigure configure);

    ICalculatorManager createCalculatorManager(ICalculatorConfigure configure);

    IResolver createResolver(IExpressionHolder holder, ICalculatorManager manager);

    ICommandDetector createCommandDetector(ICommandHolder holder);

    IExpressionHolder createExpressionHolder();

}
