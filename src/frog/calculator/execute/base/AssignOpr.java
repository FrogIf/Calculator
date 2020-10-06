package frog.calculator.execute.base;

import frog.calculator.execute.AbstractMiddleOpr;
import frog.calculator.execute.space.ISpace;
import frog.calculator.express.IExpression;
import frog.calculator.express.ValueVariableExpression;
import frog.calculator.math.number.BaseNumber;

public class AssignOpr extends AbstractMiddleOpr {

    @Override
    protected ISpace<BaseNumber> eval(IExpression left, IExpression right) {
        if(!(left instanceof ValueVariableExpression)){
            throw new IllegalArgumentException("left is not value expression");
        }
        ValueVariableExpression valueVariableExpression = (ValueVariableExpression) left;
        ISpace<BaseNumber> val = right.interpret();
        valueVariableExpression.assign(val);
        return val;
    }

}
