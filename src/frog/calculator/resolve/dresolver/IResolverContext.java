package frog.calculator.resolve.dresolver;

public interface IResolverContext {

    IResolveResultFactory getResolverResultFactory();

    AResolveResult createResolveResult();

}
