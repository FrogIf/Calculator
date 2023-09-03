package sch.frog.calculator.cell.exec.impl.base;

import sch.frog.calculator.compile.semantic.IExecuteContext;
import sch.frog.calculator.compile.syntax.ISyntaxNode;
import sch.frog.calculator.number.impl.ComplexNumber;
import sch.frog.calculator.cell.CellDyadicExecutor;

public class SubExecutor extends CellDyadicExecutor {

    @Override
    protected ComplexNumber evaluate(ISyntaxNode self, ComplexNumber childA, ComplexNumber childB,
                                     IExecuteContext context) {
        if(childA == null){
            return childB.not();
        }else{
            return childA.sub(childB);
        }
    }
}
