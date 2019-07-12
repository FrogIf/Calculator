package frog.calculator.resolver.resolve;

import frog.calculator.resolver.IResolver;
import frog.calculator.resolver.IResolverResult;

public abstract class AResolver implements IResolver {

    private IResolver next;

    private IResolverResult resolveResultPrototype;

    public AResolver(IResolverResult resolveResultPrototype) {
        this.resolveResultPrototype = resolveResultPrototype;
    }

    public void setNextResolver(IResolver resolver){
        this.next = resolver;
    }

    protected IResolver getNext(){
        return next;
    }

    @Override
    public IResolverResult resolve(char[] chars, int startIndex){
        IResolverResult resolveResult = this.resolveResultPrototype.clone();

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

    protected abstract void resolve(char[] chars, int startIndex, IResolverResult resolveResult);


}
