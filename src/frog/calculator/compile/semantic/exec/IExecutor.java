package frog.calculator.compile.semantic.exec;

import frog.calculator.compile.semantic.IExecuteContext;
import frog.calculator.compile.semantic.result.IResult;
import frog.calculator.compile.syntax.ISyntaxNode;

/**
 * 执行器
 */
public interface IExecutor {

    IResult execute(ISyntaxNode self, IExecuteContext context);

}
