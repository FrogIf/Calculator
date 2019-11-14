package frog.calculator.build.resolve;

import frog.calculator.express.IExpression;

public interface IResolverResultFactory {

    IResolverResult createResolverResultBean(IExpression expression);

}