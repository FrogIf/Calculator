package frog.calculator.resolver;

public interface IResolverContext {

    IResolveResultFactory getResolverResultFactory();

    AResolveResult createResolveResult();

}
