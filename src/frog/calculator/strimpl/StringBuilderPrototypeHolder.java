package frog.calculator.strimpl;

import frog.calculator.express.IExpression;
import frog.calculator.resolver.IResolverResult;
import frog.calculator.resolver.build.DefaultNumberExpressionFactory;
import frog.calculator.resolver.build.IBuilderPrototypeHolder;
import frog.calculator.resolver.build.INumberExpressionFactory;
import frog.calculator.resolver.result.DefaultResolveResult;

public class StringBuilderPrototypeHolder implements IBuilderPrototypeHolder {
    @Override
    public INumberExpressionFactory getNumberExpressionFactory() {
        return new DefaultNumberExpressionFactory();
    }

    @Override
    public IResolverResult getResolverResultPrototype() {
        return new DefaultResolveResult();
    }

    @Override
    public IExpression[] getPrototypeExpressions() {
        return new IExpression[0];
    }
}
