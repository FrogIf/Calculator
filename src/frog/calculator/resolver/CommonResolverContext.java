package frog.calculator.resolver;

public class CommonResolverContext implements IResolverContext{

    private CommonResolverContext(){}

    public static IResolverContext getInstance(){
        return instance;
    }

    private static CommonResolverContext instance = new CommonResolverContext();

    private IResolveResultFactory resolverResultFactory = new CommonResolveResultFactory();

    public IResolveResultFactory getResolverResultFactory() {
        return resolverResultFactory;
    }

    public AResolveResult createResolveResult(){
        return this.resolverResultFactory.createResolverResult();
    }

}
