package frog.calculator.build.command;

import frog.calculator.build.IExplainManager;
import frog.calculator.execute.holder.IExpressionHolder;

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
