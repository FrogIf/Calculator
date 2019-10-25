package frog.calculator.express.motion;

import frog.calculator.express.AbstractBlockExpression;
import frog.calculator.express.IExpression;
import frog.calculator.express.IExpressionContext;
import frog.calculator.operator.IOperator;

public class NVariableExpression extends AbstractBlockExpression {

    public enum Type{
        VALUE,
        FUNCTION
    }

    public NVariableExpression(String symbol, int buildFactor, IOperator operator) {
        super(symbol, buildFactor, operator);
    }

    /*
     * assign the variable's type.
     * different type has different action.
     * type value more like a number.
     * type function more like a function.
     */
    private Type type;

    @Override
    public boolean createBranch(IExpression childExpression) {
        return false;
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

    @Override
    public void setExpressionContext(IExpressionContext context) {

    }

    @Override
    public boolean hasNextChild() {
        return false;
    }

    @Override
    public IExpression nextChild() {
        return null;
    }
}
