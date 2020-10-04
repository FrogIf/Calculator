package frog.calculator.build.resolve;

import frog.calculator.express.IExpression;

public class CommonResolveResultFactory implements IResolveResultFactory {

    /**
     * if expression is null, return this.
     */
    private static final CommonResolveResult EMPTY_RESULT = new CommonResolveResult();

    @Override
    public IResolveResult createResolverResultBean(IExpression expression) {
        if(expression == null){
            return EMPTY_RESULT;
        }else{
            CommonResolveResult result = new CommonResolveResult();
            result.setExpression(expression);
            return result;
        }
    }
}
