package io.github.frogif.calculator.compile.semantic.exec;

import io.github.frogif.calculator.compile.semantic.result.IResult;
import io.github.frogif.calculator.compile.semantic.IExecuteContext;
import io.github.frogif.calculator.compile.syntax.ISyntaxNode;

/**
 * 执行器
 */
public interface IExecutor {

    IResult execute(ISyntaxNode self, IExecuteContext context);

}
