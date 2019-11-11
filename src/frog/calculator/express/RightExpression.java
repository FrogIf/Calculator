package frog.calculator.express;

import frog.calculator.exec.IOperator;

public class RightExpression extends AbstractBlockExpression {

    private IExpression left;

//    public RightExpression(String symbol, int buildFactor, IOperator operator, boolean fifo) {
//        super(symbol, buildFactor, operator, fifo);
//    }

    public RightExpression(String symbol, int buildFactor, IOperator operator){
        super(symbol, buildFactor, operator);
//        super(symbol, buildFactor, operator, false);
    }

    @Override
    public boolean createBranch(IExpression childExpression) {
        if(this.order > childExpression.order() && this.left == null) {
            this.left = childExpression;
            return true;
        }else{
            return false;
        }
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
        return this.left;
    }
}
