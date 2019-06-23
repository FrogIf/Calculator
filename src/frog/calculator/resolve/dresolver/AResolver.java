package frog.calculator.resolve.dresolver;

import frog.calculator.resolve.IResolveResult;
import frog.calculator.resolve.IResolver;

public abstract class AResolver implements IResolver {

    private IResolverContext context;

    protected void setResolverContext(IResolverContext context){
        this.context = context;
    }

    private IResolver next;

    protected void setNextResolver(IResolver resolver){
        this.next = resolver;
    }

    @Override
    public IResolveResult resolve(char[] chars, int startIndex){
        AResolveResult resolveResult = context.createResolveResult();

        this.resolve(chars, startIndex, resolveResult);

        if(resolveResult.getExpression() != null || this.next == null){
            return resolveResult;
        }else{
            return this.next.resolve(chars, startIndex);
        }
    }

    protected abstract void resolve(char[] chars, int startIndex, AResolveResult resolveResult);


}
