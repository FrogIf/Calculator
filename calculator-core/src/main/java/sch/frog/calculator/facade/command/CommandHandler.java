package sch.frog.calculator.facade.command;

import sch.frog.calculator.facade.ExecuteSession;
import sch.frog.calculator.facade.command.impl.NumberModeCommand;
import sch.frog.calculator.facade.command.impl.ShowASTCommand;

public class CommandHandler {

    private final CommandExecutor commandExecutor = new CommandExecutor();

    public CommandHandler(){
        commandExecutor.regist(new NumberModeCommand());
        commandExecutor.regist(new ShowASTCommand());
    }

    public boolean isCommand(String statement){
        return statement.startsWith(CommandConstants.PREFIX_MARK);
    }

    public CommandExecuteResult execute(String statement, ExecuteSession session){
        return commandExecutor.execute(statement, session);
    }

}
