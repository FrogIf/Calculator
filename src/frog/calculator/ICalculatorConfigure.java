package frog.calculator;

import frog.calculator.resolver.IResolverResultFactory;

public interface ICalculatorConfigure {

    IResolverResultFactory getResolverResultFactory();

    void setResolverResultFactory(IResolverResultFactory resolverResultFactory);

    IExpressionHolder getExpressionHolder();

    void setExpressionHolder(IExpressionHolder expressionHolder);

}
