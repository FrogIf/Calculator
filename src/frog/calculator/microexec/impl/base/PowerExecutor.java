package frog.calculator.microexec.impl.base;

import frog.calculator.compile.syntax.ISyntaxNode;
import frog.calculator.math.number.ComplexNumber;
import frog.calculator.math.number.NumberUtil;
import frog.calculator.microexec.AbstractDyadicExecutor;
import frog.calculator.microexec.MicroExecuteContext;

public class PowerExecutor extends AbstractDyadicExecutor {
    @Override
    protected ComplexNumber evaluate(ISyntaxNode self, ComplexNumber childA, ComplexNumber childB, MicroExecuteContext context) {
        return NumberUtil.power(childA, childB);
    }
}
