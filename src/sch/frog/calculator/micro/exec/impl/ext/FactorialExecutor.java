package sch.frog.calculator.micro.exec.impl.ext;

import sch.frog.calculator.compile.semantic.IExecuteContext;
import sch.frog.calculator.compile.syntax.ISyntaxNode;
import sch.frog.calculator.math.number.ComplexNumber;
import sch.frog.calculator.math.number.NumberUtil;
import sch.frog.calculator.micro.MicroSingleElementExecutor;

/**
 * 阶乘运算
 */
public class FactorialExecutor extends MicroSingleElementExecutor {

    @Override
    protected ComplexNumber evaluate(ISyntaxNode self, ComplexNumber child, IExecuteContext context) {
        return NumberUtil.factorial(child);
    }
}
