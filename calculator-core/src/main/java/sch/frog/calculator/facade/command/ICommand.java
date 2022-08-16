package sch.frog.calculator.facade.command;

import sch.frog.calculator.facade.ExecuteSession;
import sch.frog.calculator.facade.command.CommandExecuteResult;
import sch.frog.calculator.util.collection.IList;

public interface ICommand {
    /**
     * 命令字面值
     */
    String literal();

    /* 
     * 执行
     */
    CommandExecuteResult execute(ExecuteSession session, IList<String> args);

    String help();
}
