package sch.frog.calculator.cell;

import sch.frog.calculator.compile.semantic.IExecuteContext;
import sch.frog.calculator.compile.semantic.exec.AbstractSingleElementExecutor;
import sch.frog.calculator.compile.semantic.result.IValue;
import sch.frog.calculator.compile.syntax.ISyntaxNode;
import sch.frog.calculator.number.impl.ComplexNumber;

public abstract class CellSingleElementExecutor extends AbstractSingleElementExecutor {

    @Override
    protected IValue evaluate(ISyntaxNode self, IValue child, IExecuteContext context) {
        ComplexNumber num = CellUtil.getNumber(child, context);
        ComplexNumber value = this.evaluate(self, num, context);
        return new ComplexValue(value);
    }

    protected abstract ComplexNumber evaluate(ISyntaxNode self, ComplexNumber child, IExecuteContext context);
    
}
