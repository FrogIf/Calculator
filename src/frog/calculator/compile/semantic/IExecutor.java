package frog.calculator.compile.semantic;

import frog.calculator.compile.syntax.ISyntaxNode;

/**
 * 执行器
 */
public interface IExecutor {

    /**
     * 执行
     * @param context 语法树上下文
     * @return 执行结果
     */
    IResult execute(ISyntaxNode token, IExecuteContext context);

}
