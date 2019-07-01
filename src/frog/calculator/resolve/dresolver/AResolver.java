package frog.calculator.resolve.dresolver;

import frog.calculator.resolve.IResolveResult;
import frog.calculator.resolve.IResolver;

public abstract class AResolver implements IResolver {

    private static final IResolveResultFactory resultFactory = new CommonResolveResultFactory();

    private IResolver next;

    protected void setNextResolver(IResolver resolver){
        this.next = resolver;
    }

    @Override
    public IResolveResult resolve(char[] chars, int startIndex){
        AResolveResult resolveResult = resultFactory.createResolverResult();

        this.resolve(chars, startIndex, resolveResult);

        if(resolveResult.getExpression() != null){
            return resolveResult;
        }else{
            if(this.next == null){
                throw new IllegalArgumentException("can't recognize expression.");
            }
            return this.next.resolve(chars, startIndex);
        }
    }

    protected abstract void resolve(char[] chars, int startIndex, AResolveResult resolveResult);


}
