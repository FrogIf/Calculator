package sch.frog.calculator.cell.exec.impl.ext;

import sch.frog.calculator.compile.semantic.IExecuteContext;
import sch.frog.calculator.compile.syntax.ISyntaxNode;
import sch.frog.calculator.number.ComplexNumber;
import sch.frog.calculator.number.NumberUtil;
import sch.frog.calculator.cell.CellSingleElementExecutor;

/**
 * 阶乘运算
 */
public class FactorialExecutor extends CellSingleElementExecutor {

    @Override
    protected ComplexNumber evaluate(ISyntaxNode self, ComplexNumber child, IExecuteContext context) {
        return NumberUtil.factorial(child);
    }
}
