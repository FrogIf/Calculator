package io.github.frogif.calculator.cell;

import io.github.frogif.calculator.compile.semantic.IExecuteContext;
import io.github.frogif.calculator.compile.semantic.exec.AbstractDyadicExecutor;
import io.github.frogif.calculator.compile.semantic.result.IValue;
import io.github.frogif.calculator.compile.syntax.ISyntaxNode;
import io.github.frogif.calculator.number.impl.ComplexNumber;

/**
 * 二元运算
 */
public abstract class CellDyadicExecutor extends AbstractDyadicExecutor {

    @Override
    protected IValue evaluate(ISyntaxNode self, IValue childA, IValue childB, IExecuteContext context) {
        ComplexNumber numA = CellUtil.getNumber(childA, context);
        ComplexNumber numB = CellUtil.getNumber(childB, context);
        ComplexNumber resultVal = this.evaluate(self, numA, numB, context);
        return new ComplexValue(resultVal);
    }
    
    protected abstract ComplexNumber evaluate(ISyntaxNode self, ComplexNumber childA, 
        ComplexNumber childB, IExecuteContext context);
}
