package frog.calculator.resolver.resolve;

import frog.calculator.resolver.IResolver;
import frog.calculator.resolver.IResolverResult;
import frog.calculator.resolver.IResolverResultFactory;

public abstract class AbstractResolver implements IResolver {

    private IResolverResultFactory resolverResultFactory;

    public AbstractResolver(IResolverResultFactory resolverResultFactory) {
        this.resolverResultFactory = resolverResultFactory;
    }

    @Override
    public IResolverResult resolve(char[] chars, int startIndex){
        IResolverResult resolveResult = this.resolverResultFactory.createResolverResultBean();

        this.resolve(chars, startIndex, resolveResult);

        return resolveResult;
    }

    protected abstract void resolve(char[] chars, int startIndex, IResolverResult resolveResult);



}
