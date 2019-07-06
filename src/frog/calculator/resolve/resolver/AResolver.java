package frog.calculator.resolve.resolver;

import frog.calculator.resolve.IResolver;
import frog.calculator.resolve.IResolveResult;

public abstract class AResolver implements IResolver {

    private IResolveResultFactory resolveResultFactory;

    private IResolver next;

    public AResolver(IResolveResultFactory resolveResultFactory) {
        this.resolveResultFactory = resolveResultFactory;
    }

    public void setNextResolver(IResolver resolver){
        this.next = resolver;
    }

    protected IResolver getNext(){
        return next;
    }

    @Override
    public IResolveResult resolve(char[] chars, int startIndex){
        IResolveResult resolveResult = this.resolveResultFactory.createResolverResult();

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

    protected abstract void resolve(char[] chars, int startIndex, IResolveResult resolveResult);


}
