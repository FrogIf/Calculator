package frog.calculator.express;

import frog.calculator.execute.space.ISpace;
import frog.calculator.explain.IBuildFinishListener;
import frog.calculator.explain.IExpressionBuilder;
import frog.calculator.express.support.ExpressionConstant;
import frog.calculator.express.support.IExpressionContext;
import frog.calculator.math.number.BaseNumber;
import frog.calculator.util.collection.IList;

/**
 * 赋值表达式
 */
public class AssignExpression extends AbstractExpression {

    private IExpression suspendExpression;

    private IExpression valueExpression;

    private boolean leaf = false;

    public AssignExpression(String symbol) {
        super(symbol, null);
    }

    @Override
    public boolean createBranch(IExpression childExpression) {
        if(this.leaf){ return false; }

        if(childExpression.order() < this.order){
            if(suspendExpression == null){
                this.suspendExpression = childExpression;
                return true;
            }else{
                return false;
            }
        }else{
            if(valueExpression == null){
                valueExpression = childExpression;
                return true;
            }else{
                IExpression nr = valueExpression.assembleTree(childExpression);
                if(nr == null){
                    return false;
                }else{
                    this.valueExpression = nr;
                    return true;
                }
            }
        }
    }

    private IExpression reversal(){
        IExpression root = this;
        if(this.suspendExpression != null){
            root = this.suspendExpression;
            this.suspendExpression = null;
            if(!root.createBranch(this)){
                throw new IllegalStateException("the expression can't put into this tree:" + root.symbol() + ", " + this.symbol);
            }
        }

        return root;
    }

    @Override
    public IExpression assembleTree(IExpression expression) {
        if(this.leaf){
            return expression.createBranch(this) ? expression : null;
        }else if(this.order < expression.order()){
            IExpression root = null;
            if(expression.buildFactor() < 0){
                if(expression.createBranch(this)){
                    root = expression;
                }
            }else{
                if(this.createBranch(expression)){
                    root = this;
                }else if(this.valueExpression != null && this.order < expression.order()){
                    this.leaf = true;
                    root = this.reversal().assembleTree(expression);
                }
            }
            return root;
        }
        return null;
    }

    @Override
    public boolean isLeaf() {
        return this.leaf;
    }

    @Override
    public int buildFactor() {
        return ExpressionConstant.MIN_BUILD_FACTOR;
    }

    @Override
    public ISpace<BaseNumber> interpret() {
        if(this.valueExpression == null){
            throw new IllegalStateException("assign is empty.");
        }
        return this.valueExpression.interpret();
    }

    @Override
    public IList<IExpression> children() {
        return null;
    }

    @Override
    public IExpression clone() {
        AssignExpression expression = (AssignExpression) super.clone();
        if(this.valueExpression != null){
            expression.valueExpression = this.valueExpression.clone();
        }
        if(this.suspendExpression != null){
            expression.suspendExpression = this.suspendExpression.clone();
        }
        return expression;
    }

    @Override
    public void buildInit(int order, IExpressionContext context, IExpressionBuilder builder) {
        super.buildInit(order, context, builder);
        builder.addBuildFinishListener(new GoDownListener(this));
    }

    private static class GoDownListener implements IBuildFinishListener {

        private AssignExpression expression;

        private GoDownListener(AssignExpression expression) {
            this.expression = expression;
        }

        @Override
        public IExpression buildFinishCallBack(IExpressionBuilder builder) {
            if(!expression.leaf){   // 如果赋值操作还没有执行, 则执行赋值
                expression.leaf = true;
                return expression.reversal();
            }
            return builder.getRoot();
        }
    }
}
