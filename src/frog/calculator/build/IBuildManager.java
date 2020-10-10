package frog.calculator.build;

import frog.calculator.build.command.ICommandDetector;
import frog.calculator.build.resolve.IResolver;

/**
 * 解释器管理器
 */
public interface IBuildManager {

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

}
