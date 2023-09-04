package io.github.frogif.calculator.cell.exec.impl.ext;

import io.github.frogif.calculator.compile.semantic.IExecuteContext;
import io.github.frogif.calculator.compile.syntax.ISyntaxNode;
import io.github.frogif.calculator.number.impl.ComplexNumber;
import io.github.frogif.calculator.number.impl.NumberUtil;
import io.github.frogif.calculator.cell.CellSingleElementExecutor;

/**
 * 阶乘运算
 */
public class FactorialExecutor extends CellSingleElementExecutor {

    @Override
    protected ComplexNumber evaluate(ISyntaxNode self, ComplexNumber child, IExecuteContext context) {
        return NumberUtil.factorial(child);
    }
}
