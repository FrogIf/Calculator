package io.github.frogif.calculator.cell.exec.impl.base;

import io.github.frogif.calculator.cell.CellDyadicExecutor;
import io.github.frogif.calculator.compile.semantic.IExecuteContext;
import io.github.frogif.calculator.compile.syntax.ISyntaxNode;
import io.github.frogif.calculator.number.impl.ComplexNumber;

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
