package frog.calculator.build.command;

import frog.calculator.ISymbol;
import frog.calculator.build.IBuildContext;
import frog.calculator.exception.BuildException;

/**
 * 命令
 */
public interface ICommand extends ISymbol {

    /**
     * 该命令导致的表达式偏移量
     * @return 偏移量
     */
    int offset();

    /**
     * 解析之前执行
     * @param chars 待解析的表达式
     * @param startIndex 解析开始位置
     * @param buildContext 构建上下文
     */
    void preBuild(char[] chars, int startIndex, IBuildContext buildContext) throws BuildException;

    /**
     * 解析后执行
     * @param buildContext 构建上下文
     * @return 返回值指示该命令是否仍有效, true: 有效, false: 无效
     */
     boolean postBuild(IBuildContext buildContext);
}
