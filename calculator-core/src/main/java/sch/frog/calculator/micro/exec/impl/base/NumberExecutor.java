package sch.frog.calculator.micro.exec.impl.base;

import sch.frog.calculator.compile.semantic.IExecuteContext;
import sch.frog.calculator.compile.semantic.exec.IExecutor;
import sch.frog.calculator.compile.semantic.exec.exception.IncorrectStructureException;
import sch.frog.calculator.compile.semantic.result.GeneralResult;
import sch.frog.calculator.compile.semantic.result.IResult;
import sch.frog.calculator.compile.syntax.ISyntaxNode;
import sch.frog.calculator.math.number.ComplexNumber;
import sch.frog.calculator.math.number.RationalNumber;
import sch.frog.calculator.util.collection.IList;
import sch.frog.calculator.micro.ComplexValue;

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
