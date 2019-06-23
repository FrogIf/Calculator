package frog.calculator.expression;

public class MonitorExpressionWrapper implements IExpression {

    private IExpression expression;

    public MonitorExpressionWrapper(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public double interpret() {
        System.out.println(expression.toString());
        return expression.interpret();
    }

    @Override
    public int getPriority() {
        return expression.getPriority();
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
    public IExpression[] branches() {
        return this.expression.branches();
    }
}
