package frog.calculator.resolve.resolver;

import frog.calculator.resolve.result.DefaultResolveResult;
import frog.calculator.resolve.IResolveResult;

public class DefaultResolveResultFactory implements IResolveResultFactory {

    @Override
    public IResolveResult createResolverResult() {
        return new DefaultResolveResult();
    }
}
