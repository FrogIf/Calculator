package sch.frog.calculator.cell;

import sch.frog.calculator.compile.semantic.IExecuteContext;
import sch.frog.calculator.compile.semantic.exec.AbstractDyadicExecutor;
import sch.frog.calculator.compile.semantic.result.IValue;
import sch.frog.calculator.compile.syntax.ISyntaxNode;
import sch.frog.calculator.base.number.ComplexNumber;

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
