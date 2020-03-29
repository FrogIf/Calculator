package frog.calculator.explain.command;

import frog.calculator.explain.IExplainManager;
import frog.calculator.express.IExpressionHolder;

public class DefaultCommandHolder implements ICommandHolder {

    private IExplainManager explainManager;

    public DefaultCommandHolder(IExplainManager manager) {
        this.explainManager = manager;
    }

    @Override
    public ICommandFactory[] getCommandFactoryList() {
        IExpressionHolder holder = this.explainManager.getExpressionHolder();
        return new ICommandFactory[]{
                new VariableCommandFactory("@", holder, this.explainManager.getResolverFactory()),
                new BlockCommandFactory(holder.getBlockStart().symbol(), holder.getBlockEnd().symbol())
        };
    }
}
