package frog.calculator.resolver;

import frog.calculator.express.IExpression;

public interface IResolverResultFactory {

    IResolverResult createResolverResultBean(IExpression expression);

}
