package frog.calculator;

import frog.calculator.resolver.DefaultResolverResultFactory;
import frog.calculator.resolver.IResolverResultFactory;

public class DefaultCalculatorConfigure implements ICalculatorConfigure {

    private IResolverResultFactory resolverResultFactory;

    private IExpressionHolder expressionHolder;

    public DefaultCalculatorConfigure(IExpressionHolder expressionHolder) {
        this.expressionHolder = expressionHolder;
    }

    @Override
    public IResolverResultFactory getResolverResultFactory() {
        if(this.resolverResultFactory == null){
            this.resolverResultFactory = new DefaultResolverResultFactory();
        }
        return this.resolverResultFactory;
    }

    @Override
    public void setResolverResultFactory(IResolverResultFactory resolverResultFactory) {
        this.resolverResultFactory = resolverResultFactory;
    }

    @Override
    public IExpressionHolder getExpressionHolder() {
        return this.expressionHolder;
    }

    @Override
    public void setExpressionHolder(IExpressionHolder expressionHolder) {
        // do nothing. 不提供修改expressionHolder的方法, 一经赋值不允许修改(因为修改了, 也不会去重新构建解析器, 所以没有意义)
    }

}
