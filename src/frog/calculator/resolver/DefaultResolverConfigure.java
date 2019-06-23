package frog.calculator.resolver;

public class DefaultResolverConfigure implements IResolverConfigure{

    private IResolver resolver = null;

    public DefaultResolverConfigure() {
        NumberExpressionResolver numberResolver = new NumberExpressionResolver();
        MidSingleCharExpressionResolver midSingleCharResolver = new MidSingleCharExpressionResolver();
        RightSingleCharExpressionResolver rightSingleCharResolver = new RightSingleCharExpressionResolver();

        numberResolver.setNextResolver(midSingleCharResolver);
        midSingleCharResolver.setNextResolver(rightSingleCharResolver);

        this.resolver = numberResolver;
    }

    @Override
    public IResolver getResolver() {
        return this.resolver;
    }
}
