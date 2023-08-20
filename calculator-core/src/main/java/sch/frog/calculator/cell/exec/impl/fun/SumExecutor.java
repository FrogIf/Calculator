package sch.frog.calculator.cell.exec.impl.fun;

import sch.frog.calculator.compile.semantic.IExecuteContext;
import sch.frog.calculator.compile.semantic.exec.exception.IncorrectStructureException;
import sch.frog.calculator.compile.syntax.ISyntaxNode;
import sch.frog.calculator.base.number.ComplexNumber;
import sch.frog.calculator.util.collection.IList;
import sch.frog.calculator.util.collection.Iterator;
import sch.frog.calculator.cell.CellListElementExecutor;

public class SumExecutor extends CellListElementExecutor {

    @Override
    protected ComplexNumber funEvaluate(ISyntaxNode self, IList<ComplexNumber> args, IExecuteContext context) {
        if(args.isEmpty()){
            throw new IncorrectStructureException(self.word(), "there is no children");
        }
        Iterator<ComplexNumber> iterator = args.iterator();
        ComplexNumber sum = ComplexNumber.ZERO;
        while (iterator.hasNext()){
            sum = sum.add(iterator.next());
        }
        return sum;
    }
}
