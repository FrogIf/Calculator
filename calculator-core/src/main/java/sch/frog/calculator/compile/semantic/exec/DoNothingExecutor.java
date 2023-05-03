package sch.frog.calculator.compile.semantic.exec;

import sch.frog.calculator.compile.semantic.IExecuteContext;
import sch.frog.calculator.compile.semantic.result.IResult;
import sch.frog.calculator.compile.syntax.ISyntaxNode;

public class DoNothingExecutor implements IExecutor{
    @Override
    public IResult execute(ISyntaxNode self, IExecuteContext context) {
        return null;
    }
}
