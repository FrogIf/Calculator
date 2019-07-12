package frog.calculator.express.right;

import frog.calculator.express.ANaturalExpression;
import frog.calculator.express.IExpression;
import frog.calculator.operater.IOperator;

/**
 * 运算符在右边的表达式
 */
public class RightExpression extends ANaturalExpression {

    private IExpression left;

    public RightExpression(IOperator operator, int priority, String symbol) {
        super(operator, priority, symbol);
    }

    public IExpression getLeft() {
        return left;
    }

    @Override
    public boolean createBranch(IExpression expression) {
        boolean hasRebuild = false;
        if(this.left == null){
            this.left = expression;
            hasRebuild = true;
        }else{
            IExpression root = left.assembleTree(expression);
            if (root != null) {
                this.left = root;
                hasRebuild = true;
            }
        }
        return hasRebuild;
    }

}
