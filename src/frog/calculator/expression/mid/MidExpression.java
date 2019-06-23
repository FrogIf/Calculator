package frog.calculator.expression.mid;

import frog.calculator.expression.IExpression;
import frog.calculator.expression.PriorityExpression;

public abstract class MidExpression extends PriorityExpression {

    protected abstract IExpression getLeft();

    protected abstract IExpression getRight();

    protected abstract void setLeft(IExpression expression);

    protected abstract void setRight(IExpression expression);

    public boolean createBranch(IExpression expression){
        boolean hasRebuild = false;
        if(this.getRight() == null){    // 如果当前节点右侧已经有节点, 说明开始构造右子树, 这时, 不应再重新构造左子树
            IExpression leftRoot = this.createLeftTree(expression);
            if(leftRoot != null){
                this.setLeft(leftRoot);
                hasRebuild = true;
            }
        }

        if(!hasRebuild) {    // 如果左子树重新构造失败, 说明左子树已完备, 开始构造右子树
            IExpression rightRoot = this.createRightTree(expression);
            if(rightRoot != null){
                this.setRight(rightRoot);
                hasRebuild = true;
            }
        }

        return hasRebuild;
    }

    /**
     * 构造左子树
     * @param expression
     * @return
     */
    protected IExpression createLeftTree(IExpression expression){
        IExpression root;
        IExpression left = this.getLeft();
        if(left == null){
            root = expression;
        }else{
            root = left.assembleTree(expression);
        }
        return root;
    }

    /**
     * 构造右子树
     * @param expression
     * @return
     */
    protected IExpression createRightTree(IExpression expression){
        IExpression root;
        IExpression right = this.getRight();
        if(right == null){
            root = expression;
        }else{
            root = right.assembleTree(expression);
        }

        return root;
    }

    @Override
    public IExpression[] branches() {
        return new IExpression[]{getLeft(), getRight()};
    }



}
