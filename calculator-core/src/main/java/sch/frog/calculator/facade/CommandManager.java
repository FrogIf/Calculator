package sch.frog.calculator.facade;

import sch.frog.calculator.facade.commands.NumberModeCommand;
import sch.frog.calculator.facade.commands.ShowASTCommand;

public class CommandManager {

    private final CommandExecutor commandExecutor = new CommandExecutor();

    public CommandManager(){
        commandExecutor.regist(new NumberModeCommand());
        commandExecutor.regist(new ShowASTCommand());
    }

    public boolean isCommand(String statement){
        return statement.startsWith(CommandConstants.PREFIX_MARK);
    }

    public ExecResult execute(String statement, ExecuteSession session){
        return commandExecutor.execute(statement, session);
    }

}
