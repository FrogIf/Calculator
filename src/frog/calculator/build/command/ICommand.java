package frog.calculator.build.command;

import frog.calculator.ISymbol;
import frog.calculator.connect.ICalculatorSession;
import frog.calculator.exception.BuildException;
import frog.calculator.build.resolve.IResolverResult;

public interface ICommand extends ISymbol {

    /**
     * 一个新的命令初始化时执行
     * @param session 会话
     * @return 解析位置偏移量, 有些命令仅仅是命令, 有些命令既是命令又是表达式
     */
    int init(ICalculatorSession session);

    /**
     * 解析之前执行
     * @param chars 待解析的表达式
     * @param startIndex 解析开始位置
     * @param session 会话
     */
    void beforeResolve(char[] chars, int startIndex, ICalculatorSession session) throws BuildException;

    /**
     * 解析后执行
     * @param result 已经得到的解析结果
     * @param session 会话
     * @return 如果该命令不具备解析能力, 则返回传入参数本身
     */
    IResolverResult afterResolve(IResolverResult result, ICalculatorSession session);

    /**
     * 指示该命令是否已失效
     * @return true 已失效, false 依旧起作用
     */
    boolean over(char[] chars, int startIndex, ICalculatorSession session) throws BuildException;

    void buildFailedCallback(ICalculatorSession session);

}
