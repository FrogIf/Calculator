package frog.calculator.microexec.impl.ext;

import frog.calculator.compile.syntax.ISyntaxNode;
import frog.calculator.math.number.ComplexNumber;
import frog.calculator.math.number.NumberUtil;
import frog.calculator.microexec.AbstractSingleElementExecutor;
import frog.calculator.microexec.MicroExecuteContext;

public class FactorialExecutor extends AbstractSingleElementExecutor {
    @Override
    protected ComplexNumber evaluate(ISyntaxNode self, ComplexNumber child, MicroExecuteContext context) {
        return NumberUtil.factorial(child);
    }
}
