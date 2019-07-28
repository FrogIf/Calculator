package frog.calculator.resolver.resolve;

import frog.calculator.resolver.IResolver;
import frog.calculator.resolver.IResolverResult;
import frog.calculator.resolver.IResolverResultFactory;

public abstract class AbstractResolver implements IResolver {

    private IResolverResultFactory resolverResultFactory;

    public AbstractResolver(IResolverResultFactory resolverResultFactory) {
        if(resolverResultFactory == null){
            throw new IllegalArgumentException("resolverResultFactory is null : " + this.getClass().getName());
        }
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
