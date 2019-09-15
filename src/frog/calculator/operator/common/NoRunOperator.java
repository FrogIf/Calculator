package frog.calculator.operator.common;

import frog.calculator.express.IExpression;
import frog.calculator.operator.AbstractOperator;
import frog.calculator.operator.IOperator;
import frog.calculator.space.ISpace;

public class NoRunOperator extends AbstractOperator {
    @Override
    public ISpace operate(IExpression exp) {
        return exp.hasNextChild() ? exp.nextChild().interpret(): null;
    }

    private static NoRunOperator operator = new NoRunOperator();

    public static NoRunOperator getInstance(){
        return operator;
    }
}
