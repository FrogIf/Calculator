package frog.calculator.resolver.build;

import frog.calculator.express.IExpression;
import frog.calculator.resolver.IResolverResult;

public interface IBuilderPrototypeHolder {

    INumberExpressionFactory getNumberExpressionFactory();

    IResolverResult getResolverResultPrototype();

    IExpression[] getPrototypeExpressions();
}
