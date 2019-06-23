package frog.calculator.resolver;

public class CommonResolveResultFactory implements IResolveResultFactory {

    @Override
    public AResolveResult createResolverResult() {
        return new CommonResolveResult();
    }
}
