package io.github.frogif.calculator.cell.exec.impl.ext;

import io.github.frogif.calculator.compile.semantic.IExecuteContext;
import io.github.frogif.calculator.compile.syntax.ISyntaxNode;
import io.github.frogif.calculator.number.impl.ComplexNumber;
import io.github.frogif.calculator.number.impl.IntegerNumber;
import io.github.frogif.calculator.cell.CellSingleElementExecutor;

public class PercentExecutor extends CellSingleElementExecutor {

    private static final ComplexNumber ONE_HUNDRED = new ComplexNumber(IntegerNumber.valueOf(100));

    @Override
    protected ComplexNumber evaluate(ISyntaxNode self, ComplexNumber child, IExecuteContext context) {
        return child.div(ONE_HUNDRED);
    }
}
