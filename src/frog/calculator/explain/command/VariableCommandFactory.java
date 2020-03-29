package frog.calculator.explain.command;

import frog.calculator.explain.resolve.IResolverResultFactory;
import frog.calculator.express.IExpressionHolder;

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
        return new VariableDeclareCommand(this.symbol, this.holder, this.resolverResultFactory);
    }
}
