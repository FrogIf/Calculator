package io.github.frogif.calculator.cell.exec.impl.base;

import io.github.frogif.calculator.cell.ComplexValue;
import io.github.frogif.calculator.compile.semantic.IExecuteContext;
import io.github.frogif.calculator.compile.semantic.exec.IExecutor;
import io.github.frogif.calculator.compile.semantic.exec.exception.IncorrectStructureException;
import io.github.frogif.calculator.compile.semantic.result.GeneralResult;
import io.github.frogif.calculator.compile.semantic.result.IResult;
import io.github.frogif.calculator.util.collection.IList;
import io.github.frogif.calculator.compile.syntax.ISyntaxNode;
import io.github.frogif.calculator.number.impl.ComplexNumber;
import io.github.frogif.calculator.number.impl.RationalNumber;

/**
 * 数字执行器
 */
public class NumberExecutor implements IExecutor {

    @Override
    public IResult execute(ISyntaxNode self, IExecuteContext context) {
        IList<ISyntaxNode> children = self.children();
        if(children != null && children.size() != 0){
            throw new IncorrectStructureException(self.word(), "declare expect 0 child, but here is :" + children.size());
        }
        ComplexNumber number = new ComplexNumber(RationalNumber.valueOf(self.word()));
        return new GeneralResult(new ComplexValue(number));
    }
}
