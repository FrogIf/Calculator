package frog.calculator.resolve.dresolver;

import frog.calculator.resolve.IResolver;
import frog.calculator.resolve.IResolverConfigure;

public class DefaultResolverConfigure implements IResolverConfigure {

    private IResolver resolver;

    private IResolverContext context = CommonResolverContext.getInstance();

    public DefaultResolverConfigure() {
        NumberExpressionResolver numberResolver = new NumberExpressionResolver();
        numberResolver.setResolverContext(context);

        MidSingleCharExpressionResolver midSingleCharResolver = new MidSingleCharExpressionResolver();
        midSingleCharResolver.setResolverContext(context);

        RightSingleCharExpressionResolver rightSingleCharResolver = new RightSingleCharExpressionResolver();
        rightSingleCharResolver.setResolverContext(context);

        // 构造解析链
        numberResolver.setNextResolver(midSingleCharResolver);
        midSingleCharResolver.setNextResolver(rightSingleCharResolver);

        this.resolver = numberResolver;
    }

    @Override
    public IResolver getResolver() {
        return this.resolver;
    }
}
