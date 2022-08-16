package sch.frog.calculator.micro.exec.impl.base;

import sch.frog.calculator.compile.semantic.IExecuteContext;
import sch.frog.calculator.compile.syntax.ISyntaxNode;
import sch.frog.calculator.math.number.ComplexNumber;
import sch.frog.calculator.micro.MicroDyadicExecutor;

public class MultExecutor extends MicroDyadicExecutor {
    @Override
    protected ComplexNumber evaluate(ISyntaxNode self, ComplexNumber childA, ComplexNumber childB,
                                     IExecuteContext context) {
        return childA.mult(childB);
    }
}
