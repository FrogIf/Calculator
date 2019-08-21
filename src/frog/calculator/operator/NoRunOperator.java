package frog.calculator.operator;

import frog.calculator.express.IExpression;
import frog.calculator.space.ISpace;

public class NoRunOperator implements IOperator {
    @Override
    public ISpace operate(IExpression exp) {
        return exp.hasNextChild() ? exp.nextChild().interpret(): null;
    }

    private static NoRunOperator operator = new NoRunOperator();

    public static NoRunOperator getInstance(){
        return operator;
    }
}
