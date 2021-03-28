package frog.calculator.micro.exec.impl.ext;

import frog.calculator.compile.semantic.IExecuteContext;
import frog.calculator.compile.syntax.ISyntaxNode;
import frog.calculator.math.number.ComplexNumber;
import frog.calculator.math.number.NumberUtil;
import frog.calculator.micro.MicroSingleElementExecutor;

/**
 * 阶乘运算
 */
public class FactorialExecutor extends MicroSingleElementExecutor {

    @Override
    protected ComplexNumber evaluate(ISyntaxNode self, ComplexNumber child, IExecuteContext context) {
        return NumberUtil.factorial(child);
    }
}
