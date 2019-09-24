package frog.calculator.express.endpoint;

import frog.calculator.express.AbstractExpression;
import frog.calculator.express.IExpression;
import frog.calculator.express.IExpressionContext;
import frog.calculator.math.INumber;
import frog.calculator.operator.IOperator;
import frog.calculator.space.ISpace;

public class EndPointExpression extends AbstractExpression {

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
    public ISpace<INumber> interpret() {
        return operator.operate(this);
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

    @Override
    public boolean hasNextChild() {
        return false;
    }

    @Override
    public IExpression nextChild() {
        return null;
    }
}
