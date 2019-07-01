package frog.calculator.express;

import frog.calculator.express.result.ResultExpression;
import frog.calculator.operate.IOperator;

public class MonitorExpressionWrapper implements IExpression {

    private IExpression expression;

    public MonitorExpressionWrapper(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public ResultExpression interpret() {
        System.out.println(expression.toString());
        return expression.interpret();
    }

    @Override
    public int priority() {
        return expression.priority();
    }

    @Override
    public boolean isLeaf() {
        return expression.isLeaf();
    }

    @Override
    public boolean createBranch(IExpression expression) {
        return this.expression.createBranch(expression);
    }

    @Override
    public IExpression assembleTree(IExpression expression) {
        return this.expression.assembleTree(expression);
    }

    @Override
    public void setOperator(IOperator operator) {
        this.expression.setOperator(operator);
    }

    @Override
    public IExpression clone() {
        return this.expression.clone();
    }
}
