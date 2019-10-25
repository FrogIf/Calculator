package frog.calculator.express.motion;

import frog.calculator.express.AbstractExpression;
import frog.calculator.express.IExpression;
import frog.calculator.express.IExpressionContext;
import frog.calculator.operator.IOperator;

/**
 * 声明表达式
 */
public class DeclareExpression extends AbstractExpression {

    private IExpression content = null;

    private final String assignSymbol;

    private IExpression suspendExpression = null;

    public DeclareExpression(String symbol, IOperator operator, String assignSymbol) {
        super(symbol, operator);
        this.assignSymbol = assignSymbol;
    }

    @Override
    public boolean createBranch(IExpression childExpression) {
        if(childExpression.order() > this.order){
            if(content == null){
                content = childExpression;
            }else{
                IExpression root = content.assembleTree(childExpression);
                if(root != null){ content = root; }
                else{ return false; }
            }
            return true;
        }
        return false;
    }

    @Override
    public IExpression assembleTree(IExpression expression) {
        if(this.assignSymbol.equals(expression.symbol())){

        }
        if(!this.createBranch(expression)){
            return null;
        }
        return this;
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

    @Override
    public int buildFactor() {
        return 0;
    }

    @Override
    public void setExpressionContext(IExpressionContext context) {

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
