package frog.calculator.express;

import frog.calculator.operator.IOperator;

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
    public void setExpressionContext(IExpressionContext context) {
        this.context = context;
        if(this.right != null){
            this.right.setExpressionContext(context);
        }
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
