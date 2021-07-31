package sch.frog.calculator.micro.exec.impl.ext;

import sch.frog.calculator.compile.semantic.IExecuteContext;
import sch.frog.calculator.compile.syntax.ISyntaxNode;
import sch.frog.calculator.math.number.ComplexNumber;
import sch.frog.calculator.math.number.IntegerNumber;
import sch.frog.calculator.micro.MicroSingleElementExecutor;

public class PercentExecutor extends MicroSingleElementExecutor {

    private static final ComplexNumber ONE_HUNDRED = new ComplexNumber(IntegerNumber.valueOf(100));

    @Override
    protected ComplexNumber evaluate(ISyntaxNode self, ComplexNumber child, IExecuteContext context) {
        return child.div(ONE_HUNDRED);
    }
}
