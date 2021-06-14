package frog.calculator.compile.semantic;

import frog.calculator.compile.syntax.ISyntaxNode;

/**
 * 执行器
 */
public interface IExecutor {

    IResult execute(ISyntaxNode self, IExecuteContext context);

}
