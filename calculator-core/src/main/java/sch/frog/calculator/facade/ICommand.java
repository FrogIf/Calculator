package sch.frog.calculator.facade;

import sch.frog.calculator.util.collection.IList;

public interface ICommand {
    /**
     * 命令字面值
     */
    String literal();

    /* 
     * 执行
     */
    ExecResult execute(ExecuteSession session, IList<String> args);
}
