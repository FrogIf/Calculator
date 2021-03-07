package frog.calculator.microexec.impl.base;

import frog.calculator.compile.syntax.ISyntaxNode;
import frog.calculator.math.number.ComplexNumber;
import frog.calculator.microexec.AbstractDyadicExecutor;
import frog.calculator.microexec.MicroExecuteContext;

/**
 * 加法运算
 */
public class AddExecutor extends AbstractDyadicExecutor {

    @Override
    protected ComplexNumber evaluate(ISyntaxNode self, ComplexNumber childA, ComplexNumber childB,
                                     MicroExecuteContext context) {
        return childA.add(childB);
    }
}
