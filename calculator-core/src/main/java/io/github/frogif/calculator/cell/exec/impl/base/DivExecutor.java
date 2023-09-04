package io.github.frogif.calculator.cell.exec.impl.base;

import io.github.frogif.calculator.cell.CellDyadicExecutor;
import io.github.frogif.calculator.compile.semantic.IExecuteContext;
import io.github.frogif.calculator.compile.syntax.ISyntaxNode;
import io.github.frogif.calculator.number.impl.ComplexNumber;

public class DivExecutor extends CellDyadicExecutor {

    @Override
    protected ComplexNumber evaluate(ISyntaxNode self, ComplexNumber childA, ComplexNumber childB,
                                     IExecuteContext context) {
        return childA.div(childB);
    }
}
