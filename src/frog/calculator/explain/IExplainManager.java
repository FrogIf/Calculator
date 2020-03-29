package frog.calculator.explain;

import frog.calculator.explain.command.ICommandDetector;
import frog.calculator.explain.command.ICommandHolder;
import frog.calculator.explain.resolve.IResolver;
import frog.calculator.explain.resolve.IResolverResult;
import frog.calculator.explain.resolve.IResolverResultFactory;
import frog.calculator.express.IExpression;
import frog.calculator.express.IExpressionHolder;

/**
 * 解释器管理器
 */
public interface IExplainManager {

    IResolverResult assembleResolveResult(IExpression expression);

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

    IResolverResultFactory getResolverFactory();

}
