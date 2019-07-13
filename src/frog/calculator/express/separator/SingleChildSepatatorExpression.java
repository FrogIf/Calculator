package frog.calculator.express.separator;

import frog.calculator.express.IExpression;
import frog.calculator.operater.IOperator;

public abstract class SingleChildSepatatorExpression extends SeparatorExpression {

    private IExpression child;

    public SingleChildSepatatorExpression(String symbol, int buildFactor, IOperator operator) {
        super(symbol, buildFactor, operator);
    }

    protected abstract boolean checkOrder(int selfOrder, int inputOrder);

    @Override
    public boolean createBranch(IExpression childExpression) {
        if(isClose) return false;

        boolean buildSuccess = true;

        if(checkOrder(this.order, childExpression.order())){
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
        }

        isClose = !buildSuccess;

        return buildSuccess;
    }


    @Override
    public IExpression interpret() {
        return this.operator.operate(this.symbol(), this.child);
    }


}
