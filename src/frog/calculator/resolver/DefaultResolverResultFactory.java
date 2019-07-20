package frog.calculator.resolver;

public class DefaultResolverResultFactory implements IResolverResultFactory {
    @Override
    public IResolverResult createResolverResultBean() {
        return new DefaultResolveResult();
    }
}
