package frog.calculator.compile.semantic;

import frog.calculator.compile.syntax.ISyntaxNode;

/**
 * 执行器
 */
public interface IExecutor {

    void execute(ISyntaxNode token, IExecuteContext context);

}
