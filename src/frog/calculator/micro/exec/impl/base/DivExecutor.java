package frog.calculator.micro.exec.impl.base;

import frog.calculator.compile.syntax.ISyntaxNode;
import frog.calculator.math.number.ComplexNumber;
import frog.calculator.micro.exec.AbstractDyadicExecutor;
import frog.calculator.micro.exec.MicroExecuteContext;

public class DivExecutor extends AbstractDyadicExecutor {
    @Override
    protected ComplexNumber evaluate(ISyntaxNode self, ComplexNumber childA, ComplexNumber childB, MicroExecuteContext context) {
        return childA.div(childB);
    }
}
