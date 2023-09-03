package sch.frog.calculator.cell.exec.impl.fun;

import sch.frog.calculator.compile.semantic.IExecuteContext;
import sch.frog.calculator.compile.semantic.exec.exception.IncorrectStructureException;
import sch.frog.calculator.compile.syntax.ISyntaxNode;
import sch.frog.calculator.number.impl.ComplexNumber;
import sch.frog.calculator.number.impl.IntegerNumber;
import sch.frog.calculator.util.collection.IList;
import sch.frog.calculator.util.collection.Iterator;
import sch.frog.calculator.cell.CellListElementExecutor;

public class AverageExecutor extends CellListElementExecutor {

    @Override
    protected ComplexNumber funEvaluate(ISyntaxNode self, IList<ComplexNumber> args, IExecuteContext context) {
        if(args.isEmpty()){
            throw new IncorrectStructureException(self.word(), "there is no children");
        }
        Iterator<ComplexNumber> iterator = args.iterator();
        IntegerNumber count = IntegerNumber.ZERO;
        ComplexNumber sum = ComplexNumber.ZERO;
        while (iterator.hasNext()){
            sum = sum.add(iterator.next());
            count = count.add(1);
        }
        return sum.div(new ComplexNumber(count));
    }
}
