package frog.calculator.microexec.impl.base;

import frog.calculator.compile.syntax.ISyntaxNode;
import frog.calculator.math.number.ComplexNumber;
import frog.calculator.microexec.MicroExecuteContext;
import frog.calculator.microexec.AbstractMicroExecutor;
import frog.calculator.util.collection.IList;

public class BracketExecutor extends AbstractMicroExecutor {

    @Override
    protected void checkChildrenBeforeExecute(String word, IList<ISyntaxNode> children) {
        // check nothing
    }

    @Override
    protected IList<ComplexNumber> evaluate(ISyntaxNode self, IList<ComplexNumber> children,
                                            MicroExecuteContext context) {
        return children;
    }
}
