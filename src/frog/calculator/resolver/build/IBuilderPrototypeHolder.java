package frog.calculator.resolver.build;

import frog.calculator.express.IExpression;
import frog.calculator.express.end.NumberExpression;
import frog.calculator.resolver.IResolverResult;

public interface IBuilderPrototypeHolder {

    NumberExpression getNumberExpressionPrototype();

    IResolverResult getResolverResultPrototype();

    IExpression[] getPrototypeExpressions();
}
