package frog.calculator.micro.exec.impl.base;

import frog.calculator.compile.semantic.IExecuteContext;
import frog.calculator.compile.semantic.exec.IExecutor;
import frog.calculator.compile.semantic.exec.exception.IncorrectStructureException;
import frog.calculator.compile.semantic.result.GeneralResult;
import frog.calculator.compile.semantic.result.IResult;
import frog.calculator.compile.syntax.ISyntaxNode;
import frog.calculator.math.number.ComplexNumber;
import frog.calculator.math.number.RationalNumber;
import frog.calculator.micro.ComplexValue;
import frog.calculator.util.collection.IList;

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
