package frog.calculator.express.mid;

import frog.calculator.express.IExpression;
import frog.calculator.express.APriorityExpression;
import frog.calculator.operate.IOperator;

public class MidExpression extends APriorityExpression {

    private IExpression left;

    private IExpression right;

    public MidExpression(IOperator operator, int priority, String symbol) {
        super(operator, priority, symbol);
    }

    public IExpression getLeft() {
        return left;
    }

    public IExpression getRight() {
        return right;
    }

    @Override
    public boolean createBranch(IExpression expression){
        boolean hasRebuild = false;
        if(this.right == null){    // 如果当前节点右侧已经有节点, 说明开始构造右子树, 这时, 不应再重新构造左子树
            IExpression leftRoot = this.createLeftTree(expression);
            if(leftRoot != null){
                this.left = leftRoot;
                hasRebuild = true;
            }
        }

        if(!hasRebuild) {    // 如果左子树重新构造失败, 说明左子树已完备, 开始构造右子树
            IExpression rightRoot = this.createRightTree(expression);
            if(rightRoot != null){
                this.right = rightRoot;
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
        if(this.left == null){
            root = expression;
        }else{
            root = this.left.assembleTree(expression);
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
        if(this.right == null){
            root = expression;
        }else{
            root = this.right.assembleTree(expression);
        }

        return root;
    }

}
