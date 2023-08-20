package sch.frog.calculator.cell.exec.impl.base;

import sch.frog.calculator.compile.semantic.IExecuteContext;
import sch.frog.calculator.compile.syntax.ISyntaxNode;
import sch.frog.calculator.base.number.ComplexNumber;
import sch.frog.calculator.cell.CellDyadicExecutor;

/**
 * 加法运算
 */
public class AddExecutor extends CellDyadicExecutor {

    @Override
    protected ComplexNumber evaluate(ISyntaxNode self, ComplexNumber childA, ComplexNumber childB,
                                     IExecuteContext context) {
        if(childA == null){
            return childB;
        }else{
            return childA.add(childB);
        }
    }

}
