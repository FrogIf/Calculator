package io.github.frogif.calculator.cell.exec.impl.base;

import io.github.frogif.calculator.cell.CellSingleElementExecutor;
import io.github.frogif.calculator.compile.semantic.IExecuteContext;
import io.github.frogif.calculator.compile.syntax.ISyntaxNode;
import io.github.frogif.calculator.number.impl.ComplexNumber;

public class ComplexMarkExecutor extends CellSingleElementExecutor {

    @Override
    protected ComplexNumber evaluate(ISyntaxNode self, ComplexNumber child, IExecuteContext context) {
        if(child == null){
            return ComplexNumber.I;
        }else{
            return ComplexNumber.I.mult(child);
        }
    }

}
