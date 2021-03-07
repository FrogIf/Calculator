package frog.calculator.microexec.impl.ext;

import frog.calculator.compile.syntax.ISyntaxNode;
import frog.calculator.math.number.ComplexNumber;
import frog.calculator.math.number.IntegerNumber;
import frog.calculator.microexec.AbstractSingleElementExecutor;
import frog.calculator.microexec.MicroExecuteContext;

public class PercentExecutor extends AbstractSingleElementExecutor {

    private static final ComplexNumber ONE_HUNDRED = new ComplexNumber(IntegerNumber.valueOf(100));

    @Override
    protected ComplexNumber evaluate(ISyntaxNode self, ComplexNumber child, MicroExecuteContext context) {
        return child.div(ONE_HUNDRED);
    }
}
