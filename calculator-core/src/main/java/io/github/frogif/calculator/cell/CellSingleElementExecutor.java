package io.github.frogif.calculator.cell;

import io.github.frogif.calculator.compile.semantic.IExecuteContext;
import io.github.frogif.calculator.compile.semantic.exec.AbstractSingleElementExecutor;
import io.github.frogif.calculator.compile.semantic.result.IValue;
import io.github.frogif.calculator.compile.syntax.ISyntaxNode;
import io.github.frogif.calculator.number.impl.ComplexNumber;

public abstract class CellSingleElementExecutor extends AbstractSingleElementExecutor {

    @Override
    protected IValue evaluate(ISyntaxNode self, IValue child, IExecuteContext context) {
        ComplexNumber num = CellUtil.getNumber(child, context);
        ComplexNumber value = this.evaluate(self, num, context);
        return new ComplexValue(value);
    }

    protected abstract ComplexNumber evaluate(ISyntaxNode self, ComplexNumber child, IExecuteContext context);
    
}
