package frog.calculator.express.separator;

import frog.calculator.express.IExpression;
import frog.calculator.operator.IOperator;

public class AdjacencyLeftExpression extends LeftSeparatorExpression {

    public AdjacencyLeftExpression(String symbol, IOperator operator) {
        super(symbol, 0, operator);
    }

    private IExpression child;

    @Override
    public boolean createBranch(IExpression childExpression) {
        if(checkOrder(this.order, childExpression.order()) && this.child == null){
            this.child = childExpression;
            return true;
        }
        return false;
    }

    @Override
    public boolean hasNextChild() {
        return false;
    }

    @Override
    public IExpression nextChild() {
        return child;
    }
}
