package frog.calculator.microexec;

import frog.calculator.compile.syntax.ISyntaxNode;
import frog.calculator.math.number.ComplexNumber;
import frog.calculator.microexec.exception.IncorrectStructureException;
import frog.calculator.util.collection.ArrayList;
import frog.calculator.util.collection.IList;

public abstract class AbstractSingleElementExecutor extends AbstractMicroExecutor {
    @Override
    protected void checkChildrenBeforeExecute(String word, IList<ISyntaxNode> children) {
        if(children.size() != 1){
            throw new IncorrectStructureException(word, "need 1 param, but is " + children.size());
        }
    }

    @Override
    protected IList<ComplexNumber> evaluate(ISyntaxNode self, IList<ComplexNumber> children,
                                            MicroExecuteContext context) {
        ArrayList<ComplexNumber> results = new ArrayList<>(1);
        ComplexNumber number = this.evaluate(self, children.get(0), context);
        results.add(number);
        return results;
    }

    protected abstract ComplexNumber evaluate(ISyntaxNode self, ComplexNumber child,
                                              MicroExecuteContext context);
}
