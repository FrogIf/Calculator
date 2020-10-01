package frog.calculator.express;

import frog.calculator.build.IBuildContext;
import frog.calculator.execute.space.ISpace;
import frog.calculator.express.support.IExpressionContext;
import frog.calculator.math.number.BaseNumber;
import frog.calculator.util.collection.IList;

/**
 * 幽灵表达式
 * 不参与运算, 类似于哨兵或者占位
 */
public class GhostExpression implements IExpression {

    private IExpression root;

    @Override
    public boolean createBranch(IExpression childExpression) {
        boolean success = true;
        if(root == null){
            root = childExpression;
        }else{
            IExpression tRoot = root.assembleTree(childExpression);
            success = tRoot!= null;
            if(success){
                this.root = tRoot;
            }
        }
        return success;
    }

    @Override
    public IExpression assembleTree(IExpression expression) {
        if(root == null){
            root = expression;
            return root;
        }else{
            return root.assembleTree(expression);
        }
    }

    @Override
    public boolean isLeaf() {
        return root != null && root.isLeaf();
    }

    @Override
    public int buildFactor() {
        return root == null ? MIN_BUILD_FACTOR : root.buildFactor();
    }

    @Override
    public IExpression newInstance() {
        GhostExpression ghost = new GhostExpression();
        ghost.root = this.root == null ? null : this.root.newInstance();
        return ghost;
    }

    @Override
    public int order() {
        return this.root == null ? 0 : this.root.order();
    }

    @Override
    public ISpace<BaseNumber> interpret() {
        return this.root == null ? null : this.root.interpret();
    }

    @Override
    public IList<IExpression> children() {
        return this.root == null ? null : this.root.children();
    }

    @Override
    public IExpressionContext getContext() {
        return this.root == null ? null : this.root.getContext();
    }

    @Override
    public void buildInit(int order, IExpressionContext context, IBuildContext buildContext) {
        // do nothing
    }

    @Override
    public String symbol() {
        return this.root == null ? null : this.root.symbol();
    }

}
