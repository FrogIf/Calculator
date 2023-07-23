package sch.frog.calculator.cell.exec.impl.base;

import sch.frog.calculator.compile.semantic.IExecuteContext;
import sch.frog.calculator.compile.syntax.ISyntaxNode;
import sch.frog.calculator.number.ComplexNumber;
import sch.frog.calculator.cell.CellDyadicExecutor;
import sch.frog.calculator.cell.exception.UnrecognizedSymbolException;

public class PlusMinusExecutor extends CellDyadicExecutor {
    @Override
    protected ComplexNumber evaluate(ISyntaxNode self, ComplexNumber childA, ComplexNumber childB, IExecuteContext context) {
        String word = self.word();
        if("+".equals(word)){
            return add(childA, childB);
        }else if("-".equals(word)){
            return sub(childA, childB);
        }else{
            throw new UnrecognizedSymbolException(word);
        }
    }

    private ComplexNumber add(ComplexNumber childA, ComplexNumber childB){
        if(childA == null){
            return childB;
        }else{
            return childA.add(childB);
        }
    }

    private ComplexNumber sub(ComplexNumber childA, ComplexNumber childB){
        if(childA == null){
            return childB.not();
        }else{
            return childA.sub(childB);
        }
    }
}
