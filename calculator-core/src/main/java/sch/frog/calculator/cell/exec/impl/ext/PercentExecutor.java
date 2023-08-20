package sch.frog.calculator.cell.exec.impl.ext;

import sch.frog.calculator.compile.semantic.IExecuteContext;
import sch.frog.calculator.compile.syntax.ISyntaxNode;
import sch.frog.calculator.base.number.ComplexNumber;
import sch.frog.calculator.base.number.IntegerNumber;
import sch.frog.calculator.cell.CellSingleElementExecutor;

public class PercentExecutor extends CellSingleElementExecutor {

    private static final ComplexNumber ONE_HUNDRED = new ComplexNumber(IntegerNumber.valueOf(100));

    @Override
    protected ComplexNumber evaluate(ISyntaxNode self, ComplexNumber child, IExecuteContext context) {
        return child.div(ONE_HUNDRED);
    }
}
