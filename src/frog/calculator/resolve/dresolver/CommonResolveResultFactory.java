package frog.calculator.resolve.dresolver;

public class CommonResolveResultFactory implements IResolveResultFactory {

    @Override
    public AResolveResult createResolverResult() {
        return new CommonResolveResult();
    }
}
