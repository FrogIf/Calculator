package io.github.frogif.calculator.compile.semantic.exec;

import io.github.frogif.calculator.compile.semantic.result.IResult;
import io.github.frogif.calculator.compile.semantic.IExecuteContext;
import io.github.frogif.calculator.compile.syntax.ISyntaxNode;

public class DoNothingExecutor implements IExecutor{
    @Override
    public IResult execute(ISyntaxNode self, IExecuteContext context) {
        return null;
    }
}
