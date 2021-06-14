package frog.calculator.micro;

import frog.calculator.common.exec.AbstractSingleElementExecutor;
import frog.calculator.compile.semantic.IExecuteContext;
import frog.calculator.compile.semantic.IValue;
import frog.calculator.compile.syntax.ISyntaxNode;
import frog.calculator.math.number.ComplexNumber;

public abstract class MicroSingleElementExecutor extends AbstractSingleElementExecutor {

    @Override
    protected IValue evaluate(ISyntaxNode self, IValue child, IExecuteContext context) {
        ComplexNumber num = MicroUtil.getNumber(child, context);
        ComplexNumber value = this.evaluate(self, num, context);
        return new ComplexValue(value);
    }

    protected abstract ComplexNumber evaluate(ISyntaxNode self, ComplexNumber child, IExecuteContext context);
    
}
