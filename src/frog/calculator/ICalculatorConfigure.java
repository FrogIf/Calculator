package frog.calculator;

import frog.calculator.resolver.IResolverResultFactory;
import frog.calculator.resolver.resolve.factory.INumberExpressionFactory;

public interface ICalculatorConfigure {

    IResolverResultFactory getResolverResultFactory();

    void setResolverResultFactory(IResolverResultFactory resolverResultFactory);

    IExpressionHolder getExpressionHolder();

    void setExpressionHolder(IExpressionHolder expressionHolder);

    INumberExpressionFactory getNumberExpressionFactory();

    void setNumberExpressionFactory(INumberExpressionFactory numberExpressionFactory);


}
