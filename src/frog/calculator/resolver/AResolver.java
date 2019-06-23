package frog.calculator.resolver;

public abstract class AResolver implements IResolver {
    private final IResolverContext context = CommonResolverContext.getInstance();

    private IResolver next;

    void setNextResolver(IResolver resolver){
        this.next = resolver;
    }

    IResolver getNextResolver(){
        return this.next;
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
