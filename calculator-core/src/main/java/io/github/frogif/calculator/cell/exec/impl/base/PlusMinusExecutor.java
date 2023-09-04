package io.github.frogif.calculator.cell.exec.impl.base;

import io.github.frogif.calculator.cell.CellDyadicExecutor;
import io.github.frogif.calculator.cell.exception.UnrecognizedSymbolException;
import io.github.frogif.calculator.compile.semantic.IExecuteContext;
import io.github.frogif.calculator.compile.syntax.ISyntaxNode;
import io.github.frogif.calculator.number.impl.ComplexNumber;

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
