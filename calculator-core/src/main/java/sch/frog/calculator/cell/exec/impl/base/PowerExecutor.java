package sch.frog.calculator.cell.exec.impl.base;

import sch.frog.calculator.compile.semantic.IExecuteContext;
import sch.frog.calculator.compile.semantic.exec.exception.IncorrectStructureException;
import sch.frog.calculator.compile.syntax.ISyntaxNode;
import sch.frog.calculator.number.ComplexNumber;
import sch.frog.calculator.number.NumberUtil;
import sch.frog.calculator.util.StringUtils;
import sch.frog.calculator.cell.CellDyadicExecutor;

public class PowerExecutor extends CellDyadicExecutor {

    @Override
    protected ComplexNumber evaluate(ISyntaxNode self, ComplexNumber childA, ComplexNumber childB,
                                     IExecuteContext context) {
        if(childA == null || childB == null){
            throw new IncorrectStructureException(self.word(), "param incorrect, a : " + StringUtils.valueOf(childA) + ", b : " + StringUtils.valueOf(childB));
        }
        return NumberUtil.power(childA, childB);
    }
}
