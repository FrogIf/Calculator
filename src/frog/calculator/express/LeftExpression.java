package frog.calculator.express;

import frog.calculator.exec.IOperator;

public class LeftExpression extends AbstractBlockExpression {

    private IExpression right;

//    public LeftExpression(String symbol, int buildFactor, IOperator operator, boolean fifo) {
//        super(symbol, buildFactor, operator, fifo);
//    }

    public LeftExpression(String symbol, int buildFactor, IOperator operator){
        super(symbol, buildFactor, operator);
    }

    @Override
    public boolean createBranch(IExpression childExpression) {
        if(this.order < childExpression.order() && this.right == null){
            this.right = childExpression;
            return true;
        }
        return false;
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

    @Override
    public boolean hasNextChild() {
        return false;
    }

    @Override
    public IExpression nextChild() {
        return this.right;
    }
}
