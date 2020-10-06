package frog.calculator.build.command;

import frog.calculator.build.IBuildManager;
import frog.calculator.execute.holder.IExpressionHolder;

public class DefaultCommandHolder implements ICommandHolder {

    private final IBuildManager explainManager;

    public DefaultCommandHolder(IBuildManager manager) {
        this.explainManager = manager;
    }

    @Override
    public ICommandFactory[] getCommandFactoryList() {
        IExpressionHolder holder = this.explainManager.getExpressionHolder();
        return new ICommandFactory[]{
                new VariableCommandFactory("@", holder.getAssign().symbol()),
                new BlockCommandFactory(holder.getBlockStart().symbol(), holder.getBlockEnd().symbol())
        };
    }
}
