package frog.calculator.build.command;

import frog.calculator.build.resolve.CommonResolveResultFactory;
import frog.calculator.build.resolve.IResolveResultFactory;
import frog.calculator.execute.holder.IExpressionHolder;

public class VariableCommandFactory extends AbstractCommandFactory {

    private IExpressionHolder holder;

    private static final IResolveResultFactory resolverResultFactory = new CommonResolveResultFactory();

    public VariableCommandFactory(String symbol, IExpressionHolder holder) {
        super(symbol);
        this.holder = holder;
    }

    @Override
    public ICommand instance() {
        return new ValueVariableDeclareCommand(this.symbol, new String[]{holder.getAssign().symbol()}, resolverResultFactory);
    }
}
