package frog.calculator.build.command;

import frog.calculator.ISymbol;
import frog.calculator.build.IExpressionBuilder;
import frog.calculator.build.resolve.IResolverResult;
import frog.calculator.exception.BuildException;

public interface ICommand extends ISymbol {

    /**
     * 一个新的命令初始化时执行
     * @param builder 表达式构建器
     * @return 解析位置偏移量, 有些命令仅仅是命令, 有些命令既是命令又是表达式
     */
    int init(IExpressionBuilder builder);

    /**
     * 解析之前执行
     * @param chars 待解析的表达式
     * @param startIndex 解析开始位置
     * @param builder 表达式构建器
     */
    void beforeResolve(char[] chars, int startIndex, IExpressionBuilder builder) throws BuildException;

    /**
     * 解析后执行
     * @param result 已经得到的解析结果
     * @param builder 表达式构建器
     * @return 如果该命令不具备解析能力, 则返回传入参数本身
     */
    IResolverResult afterResolve(IResolverResult result, IExpressionBuilder builder);

    /**
     * 指示该命令是否已失效
     * @return true 已失效, false 依旧起作用
     */
    boolean over(String symbol, IExpressionBuilder builder) throws BuildException;

}
