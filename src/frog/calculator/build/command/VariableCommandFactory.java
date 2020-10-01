package frog.calculator.build.command;

import frog.calculator.build.resolve.IResolverResultFactory;
import frog.calculator.execute.holder.IExpressionHolder;

public class VariableCommandFactory extends AbstractCommandFactory {

    private IExpressionHolder holder;

    private IResolverResultFactory resolverResultFactory;

    public VariableCommandFactory(String symbol, IExpressionHolder holder, IResolverResultFactory resolverResultFactory) {
        super(symbol);
        this.holder = holder;
        this.resolverResultFactory = resolverResultFactory;
    }

    @Override
    public ICommand instance() {
        return new ValueVariableDeclareCommand(this.symbol, null, null);
    }
}
