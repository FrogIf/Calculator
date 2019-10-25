package frog.calculator.express.motion;

import frog.calculator.express.AbstractExpression;
import frog.calculator.express.IExpression;
import frog.calculator.express.IExpressionContext;
import frog.calculator.operator.IOperator;

/**
 * 赋值表达式
 */
public class AssignExpression extends AbstractExpression {

    public AssignExpression(String symbol, IOperator operator) {
        super(symbol, operator);
    }

    @Override
    public boolean createBranch(IExpression childExpression) {
        return false;
    }

    @Override
    public IExpression assembleTree(IExpression expression) {
        return null;
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

    @Override
    public int buildFactor() {
        return 0;
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
