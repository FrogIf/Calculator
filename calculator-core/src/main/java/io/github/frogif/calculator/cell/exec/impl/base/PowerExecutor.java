package io.github.frogif.calculator.cell.exec.impl.base;

import io.github.frogif.calculator.cell.CellDyadicExecutor;
import io.github.frogif.calculator.compile.semantic.IExecuteContext;
import io.github.frogif.calculator.compile.semantic.exec.exception.IncorrectStructureException;
import io.github.frogif.calculator.util.StringUtils;
import io.github.frogif.calculator.compile.syntax.ISyntaxNode;
import io.github.frogif.calculator.number.impl.ComplexNumber;
import io.github.frogif.calculator.number.impl.NumberUtil;

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
