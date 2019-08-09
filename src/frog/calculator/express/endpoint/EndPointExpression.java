package frog.calculator.express.endpoint;

import frog.calculator.express.AbstractExpression;
import frog.calculator.express.IExpression;
import frog.calculator.express.IExpressionContext;
import frog.calculator.operator.IOperator;

public class EndPointExpression extends AbstractExpression {

    private IOperator operator;

    protected IExpressionContext context;

    public EndPointExpression(String symbol, IOperator operator) {
        super(symbol, operator);
        this.operator = operator;
    }

    @Override
    public final boolean createBranch(IExpression childExpression) {
        // endpoint can't create branch.
        return false;
    }

    @Override
    public IExpression assembleTree(IExpression expression) {
        if(expression.isLeaf()){
            return null;
        }else{
            return expression.assembleTree(this);
        }
    }

    @Override
    public IExpression interpret() {
        return operator.operate(this.symbol(), context, new IExpression[]{this});
    }

    @Override
    public final boolean isLeaf() {
        return true;
    }

    @Override
    public int buildFactor() {
        // because of it is leaf, so this is not important.
        return 1;
    }

    @Override
    public IExpression clone() {
        return super.clone();
    }

    @Override
    public void setExpressionContext(IExpressionContext context) {
        this.context = context;
    }
}
