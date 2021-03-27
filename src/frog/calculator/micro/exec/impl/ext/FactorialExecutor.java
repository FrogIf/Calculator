package frog.calculator.micro.exec.impl.ext;

import frog.calculator.compile.syntax.ISyntaxNode;
import frog.calculator.math.number.ComplexNumber;
import frog.calculator.math.number.NumberUtil;
import frog.calculator.micro.exec.AbstractSingleElementExecutor;
import frog.calculator.micro.exec.MicroExecuteContext;

public class FactorialExecutor extends AbstractSingleElementExecutor {
    @Override
    protected ComplexNumber evaluate(ISyntaxNode self, ComplexNumber child, MicroExecuteContext context) {
        return NumberUtil.factorial(child);
    }
}
