package frog.calculator.explain.resolve;

public abstract class AbstractResolver implements IResolver {

    protected IResolverResultFactory resolverResultFactory;

    public AbstractResolver(IResolverResultFactory resolverResultFactory) {
        if(resolverResultFactory == null){
            throw new IllegalArgumentException("resolve result factory is null.");
        }
        this.resolverResultFactory = resolverResultFactory;
    }
}
