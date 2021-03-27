package frog.calculator.micro.exec.impl.fun;

import frog.calculator.compile.syntax.ISyntaxNode;
import frog.calculator.math.number.ComplexNumber;
import frog.calculator.math.number.IntegerNumber;
import frog.calculator.micro.exception.IncorrectStructureException;
import frog.calculator.micro.exec.AbstractMicroExecutor;
import frog.calculator.micro.exec.MicroExecuteContext;
import frog.calculator.util.collection.ArrayList;
import frog.calculator.util.collection.IList;
import frog.calculator.util.collection.Iterator;

public class AverageExecutor extends AbstractMicroExecutor {

    @Override
    protected IList<ComplexNumber> evaluate(ISyntaxNode self, IList<ComplexNumber> children,
                                            MicroExecuteContext context) {
        if(children.isEmpty()){
            throw new IncorrectStructureException(self.word(), "there is no children");
        }
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
