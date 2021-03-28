package frog.calculator.micro.exec.impl.ext;

import frog.calculator.compile.semantic.IExecuteContext;
import frog.calculator.compile.syntax.ISyntaxNode;
import frog.calculator.math.number.ComplexNumber;
import frog.calculator.math.number.IntegerNumber;
import frog.calculator.micro.MicroSingleElementExecutor;

public class PercentExecutor extends MicroSingleElementExecutor {

    private static final ComplexNumber ONE_HUNDRED = new ComplexNumber(IntegerNumber.valueOf(100));

    @Override
    protected ComplexNumber evaluate(ISyntaxNode self, ComplexNumber child, IExecuteContext context) {
        return child.div(ONE_HUNDRED);
    }
}
