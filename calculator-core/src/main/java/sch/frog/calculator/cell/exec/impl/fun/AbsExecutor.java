package sch.frog.calculator.cell.exec.impl.fun;

import sch.frog.calculator.compile.semantic.IExecuteContext;
import sch.frog.calculator.compile.syntax.ISyntaxNode;
import sch.frog.calculator.number.impl.ComplexNumber;
import sch.frog.calculator.number.impl.NumberSign;
import sch.frog.calculator.number.impl.RationalNumber;
import sch.frog.calculator.cell.CellSingleElementExecutor;

public class AbsExecutor extends CellSingleElementExecutor {
    @Override
    protected ComplexNumber evaluate(ISyntaxNode self, ComplexNumber child, IExecuteContext context) {
        RationalNumber imaginaryPart = child.getImaginaryPart();
        RationalNumber realPart = child.getRealPart();
        if(realPart != null){
            NumberSign sr = realPart.getSign();
            if(sr == NumberSign.NEGATIVE){
                realPart = realPart.not();
            }
        }
        if(imaginaryPart == null || imaginaryPart.equals(RationalNumber.ZERO)){
            return new ComplexNumber(realPart, imaginaryPart);
        }

        // TODO 求取复数的模
        return null;
    }
}
