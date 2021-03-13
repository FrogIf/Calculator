package frog.calculator.microexec;

import frog.calculator.compile.syntax.ISyntaxNode;
import frog.calculator.math.number.ComplexNumber;
import frog.calculator.microexec.exception.IncorrectStructureException;
import frog.calculator.util.collection.ArrayList;
import frog.calculator.util.collection.IList;

/**
 * 二值运算
 */
public abstract class AbstractDyadicExecutor extends AbstractMicroExecutor {

    @Override
    protected IList<ComplexNumber> evaluate(ISyntaxNode self, IList<ComplexNumber> children, MicroExecuteContext context) {
        if(children.size() < 2){
            throw new IncorrectStructureException(self.word(), "need 2 param, but is " + children.size());
        }
        ArrayList<ComplexNumber> results = new ArrayList<>(1);
        ComplexNumber number = this.evaluate(self, children.get(0), children.get(1), context);
        results.add(number);
        return results;
    }

    protected abstract ComplexNumber evaluate(ISyntaxNode self, ComplexNumber childA, ComplexNumber childB, MicroExecuteContext context);
}
