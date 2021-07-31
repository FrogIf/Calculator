package sch.frog.calculator.compile.semantic.exec;

import sch.frog.calculator.compile.semantic.IExecuteContext;
import sch.frog.calculator.compile.semantic.result.IResult;
import sch.frog.calculator.compile.syntax.ISyntaxNode;

/**
 * 执行器
 */
public interface IExecutor {

    IResult execute(ISyntaxNode self, IExecuteContext context);

}
