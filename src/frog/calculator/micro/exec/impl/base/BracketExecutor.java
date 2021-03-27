package frog.calculator.micro.exec.impl.base;

import frog.calculator.compile.syntax.ISyntaxNode;
import frog.calculator.math.number.ComplexNumber;
import frog.calculator.micro.exec.AbstractMicroExecutor;
import frog.calculator.micro.exec.MicroExecuteContext;
import frog.calculator.util.collection.IList;

public class BracketExecutor extends AbstractMicroExecutor {

    @Override
    protected IList<ComplexNumber> evaluate(ISyntaxNode self, IList<ComplexNumber> children,
                                            MicroExecuteContext context) {
        return children;
    }
}
