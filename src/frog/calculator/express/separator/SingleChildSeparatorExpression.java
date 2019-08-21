package frog.calculator.express.separator;

import frog.calculator.express.IExpression;
import frog.calculator.express.IExpressionContext;
import frog.calculator.operator.IOperator;

public abstract class SingleChildSeparatorExpression extends SeparatorExpression {

    private IExpression child;

    private IExpressionContext context;

    public SingleChildSeparatorExpression(String symbol, int buildFactor, IOperator operator, boolean fifo) {
        super(symbol, buildFactor, operator, fifo);
    }

    protected abstract boolean checkOrder(int selfOrder, int inputOrder);

    @Override
    public boolean createBranch(IExpression childExpression) {
        if(checkOrder(this.order, childExpression.order())){
            boolean buildSuccess = true;
            if(this.child == null){
                this.child = childExpression;
            }else{
                IExpression childTree = this.child.assembleTree(childExpression);
                if(childTree != null){
                    this.child = childTree;
                }else{
                    buildSuccess = false;
                }
            }
            return buildSuccess;
        }else{
            return false;
        }
    }

    @Override
    public void setExpressionContext(IExpressionContext context) {
        this.context = context;
        if(this.child != null){
            this.child.setExpressionContext(context);
        }
    }
}
