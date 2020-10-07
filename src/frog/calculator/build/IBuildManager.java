package frog.calculator.build;

import frog.calculator.build.command.ICommandDetector;
import frog.calculator.build.command.ICommandHolder;
import frog.calculator.build.resolve.IResolver;
import frog.calculator.express.holder.IExpressionHolder;

/**
 * 解释器管理器
 */
public interface IBuildManager {

    ICommandHolder getCommandHolder();

    /**
     * 运算符解析器
     * @return 解析器
     */
    IResolver getResolver();

    /**
     * 命令探查器
     * @return 探查器
     */
    ICommandDetector getCommandDetector();

    IExpressionHolder getExpressionHolder();

}
