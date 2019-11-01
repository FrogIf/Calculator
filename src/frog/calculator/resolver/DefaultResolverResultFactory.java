package frog.calculator.resolver;

import frog.calculator.express.IExpression;

public class DefaultResolverResultFactory implements IResolverResultFactory {
    @Override
    public IResolverResult createResolverResultBean(IExpression expression) {
        DefaultResolveResult result = new DefaultResolveResult();
        result.setExpression(expression);
        return result;
    }
}
