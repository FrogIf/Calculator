package frog.calculator.expression.right;

import frog.calculator.expression.IExpression;
import frog.calculator.expression.PriorityExpression;

/**
 * 运算符在右边的表达式
 */
public abstract class RightExpression extends PriorityExpression {

    public abstract double interpret();

    public abstract IExpression getLeft();

    public abstract void setLeft(IExpression left);

    @Override
    public abstract int getPriority();

    @Override
    public boolean createBranch(IExpression expression) {
        boolean hasRebuild = false;
        IExpression left = this.getLeft();
        if(left == null){
            this.setLeft(expression);
            hasRebuild = true;
        }else{
            left.assembleTree(expression);
        }
        return hasRebuild;
    }

    @Override
    public IExpression[] branches() {
        return new IExpression[]{getLeft()};
    }

}
