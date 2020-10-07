package frog.calculator.build.command;

import frog.calculator.express.holder.OriginExpressionHolder;

public class DefaultCommandHolder implements ICommandHolder {

    @Override
    public ICommandFactory[] getCommandFactoryList() {
        return new ICommandFactory[]{
                new VariableCommandFactory("@", OriginExpressionHolder.ASSIGN),
                new BlockCommandFactory("{", "}")
        };
    }
}
