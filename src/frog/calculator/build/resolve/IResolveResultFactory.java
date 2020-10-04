package frog.calculator.build.resolve;

import frog.calculator.express.IExpression;

public interface IResolveResultFactory {

    IResolveResult createResolverResultBean(IExpression expression);

}
