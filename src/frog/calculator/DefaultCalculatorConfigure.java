package frog.calculator;

import frog.calculator.resolver.DefaultResolverResultFactory;
import frog.calculator.resolver.IResolverResultFactory;

public class DefaultCalculatorConfigure implements ICalculatorConfigure {

    private IResolverResultFactory resolverResultFactory;

    private IExpressionHolder expressionHolder;

    private ICommandHolder commandHolder;

    public void setExpressionHolder(IExpressionHolder expressionHolder){
        this.expressionHolder = expressionHolder;
    }

    public void setCommandHolder(ICommandHolder commandHolder){
        this.commandHolder = commandHolder;
    }

    @Override
    public IResolverResultFactory getResolverResultFactory() {
        if(this.resolverResultFactory == null){
            this.resolverResultFactory = new DefaultResolverResultFactory();
        }
        return this.resolverResultFactory;
    }

    @Override
    public IExpressionHolder getExpressionHolder() {
        return this.expressionHolder;
    }

    @Override
    public ICommandHolder getCommandHolder() {
        return this.commandHolder;
    }

}
