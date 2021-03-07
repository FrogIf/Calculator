package frog.calculator.microexec.impl.fun;

import frog.calculator.compile.syntax.ISyntaxNode;
import frog.calculator.math.number.ComplexNumber;
import frog.calculator.math.number.IntegerNumber;
import frog.calculator.microexec.AbstractMicroExecutor;
import frog.calculator.microexec.MicroExecuteContext;
import frog.calculator.microexec.exception.IncorrectStructureException;
import frog.calculator.util.collection.ArrayList;
import frog.calculator.util.collection.IList;
import frog.calculator.util.collection.Iterator;

public class AverageExecutor extends AbstractMicroExecutor {
    @Override
    protected void checkChildrenBeforeExecute(String word, IList<ISyntaxNode> children) {
        if(children.isEmpty()){
            throw new IncorrectStructureException(word, "there is no children");
        }
    }

    @Override
    protected IList<ComplexNumber> evaluate(ISyntaxNode self, IList<ComplexNumber> children,
                                            MicroExecuteContext context) {
        Iterator<ComplexNumber> iterator = children.iterator();
        IntegerNumber count = IntegerNumber.ZERO;
        ComplexNumber sum = ComplexNumber.ZERO;
        while (iterator.hasNext()){
            sum = sum.add(iterator.next());
            count = count.add(1);
        }
        ComplexNumber avg = sum.div(new ComplexNumber(count));
        ArrayList<ComplexNumber> result = new ArrayList<>(1);
        result.add(avg);
        return result;
    }
}
