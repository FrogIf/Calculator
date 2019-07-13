package frog.calculator.express.endpoint;

import frog.calculator.express.AExpression;
import frog.calculator.express.IExpression;
import frog.calculator.express.IExpressionContext;
import frog.calculator.operater.IOperator;

public class EndPointExpression extends AExpression {

    private IExpressionContext context;

    private IOperator operator;

    public EndPointExpression(String symbol, IOperator operator) {
        super(symbol, operator);
        this.operator = operator;
    }

    @Override
    public final boolean createBranch(IExpression childExpression) {
        // endpoint 表达式不能创建分支
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
        return operator.operate(this.symbol(), this);
    }

    @Override
    public final boolean isLeaf() {
        return true;
    }

    @Override
    public int buildFactor() {
        return this.context.getCurrentMaxBuildFactor();
    }

    @Override
    public void setExpressionContext(IExpressionContext context) {
        this.context = context;
    }

    @Override
    public IExpression clone() {
        return super.clone();
    }
}
