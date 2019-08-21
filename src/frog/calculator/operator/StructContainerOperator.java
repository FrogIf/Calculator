package frog.calculator.operator;

import frog.calculator.express.IExpression;
import frog.calculator.space.ISpace;

public class StructContainerOperator implements IOperator {

    @Override
    public ISpace operate(IExpression exp) {
        return exp.nextChild().interpret();
    }

}
