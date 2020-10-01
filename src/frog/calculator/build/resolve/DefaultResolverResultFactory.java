package frog.calculator.build.resolve;

import frog.calculator.express.IExpression;

public class DefaultResolverResultFactory implements IResolverResultFactory {

    /**
     * if expression is null, return this.
     * FIXME 需要对象内部的不变性
     */
    private static final DefaultResolveResult EMPTY_RESULT = new DefaultResolveResult();

    @Override
    public IResolverResult createResolverResultBean(IExpression expression) {
        if(expression == null){
            return EMPTY_RESULT;
        }else{
            DefaultResolveResult result = new DefaultResolveResult();
            result.setExpression(expression);
            return result;
        }
    }
}
