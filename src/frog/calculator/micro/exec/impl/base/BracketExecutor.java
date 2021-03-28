package frog.calculator.micro.exec.impl.base;

import frog.calculator.common.exec.AbstractSingleElementExecutor;
import frog.calculator.compile.semantic.IExecuteContext;
import frog.calculator.compile.semantic.IValue;
import frog.calculator.compile.syntax.ISyntaxNode;

public class BracketExecutor extends AbstractSingleElementExecutor {

    @Override
    protected IValue evaluate(ISyntaxNode self, IValue child, IExecuteContext context) {
        return child;
    }
}
