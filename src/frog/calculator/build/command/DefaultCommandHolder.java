package frog.calculator.build.command;

import frog.calculator.express.holder.MathExpressionHolder;

public class DefaultCommandHolder implements ICommandHolder {

    @Override
    public ICommandFactory[] getCommandFactoryList() {
        return new ICommandFactory[]{
                new VariableCommandFactory("@", MathExpressionHolder.ASSIGN),
                new BlockCommandFactory("{", "}")
        };
    }
}
