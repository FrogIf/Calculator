package sch.frog.calculator.micro.exec.impl.base;

import sch.frog.calculator.compile.semantic.IExecuteContext;
import sch.frog.calculator.compile.semantic.exec.AbstractSingleElementExecutor;
import sch.frog.calculator.compile.semantic.result.IValue;
import sch.frog.calculator.compile.syntax.ISyntaxNode;

public class BracketExecutor extends AbstractSingleElementExecutor {

    @Override
    protected IValue evaluate(ISyntaxNode self, IValue child, IExecuteContext context) {
        return child;
    }
}
