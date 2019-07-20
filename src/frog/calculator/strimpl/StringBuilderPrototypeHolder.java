package frog.calculator.strimpl;

import frog.calculator.express.IExpression;
import frog.calculator.resolver.DefaultResolveResult;
import frog.calculator.resolver.IResolverResult;
import frog.calculator.resolver.resolve.factory.DefaultNumberExpressionFactory;
import frog.calculator.resolver.resolve.factory.INumberExpressionFactory;

public class StringBuilderPrototypeHolder {
    public INumberExpressionFactory getNumberExpressionFactory() {
        return new DefaultNumberExpressionFactory();
    }

    public IResolverResult getResolverResultPrototype() {
        return new DefaultResolveResult();
    }

    public IExpression[] getPrototypeExpressions() {
        return new IExpression[0];
    }

    public IExpression getAddExpressionPrototype() {
        return null;
    }

    public IExpression getSubExpressionPrototype() {
        return null;
    }
}
