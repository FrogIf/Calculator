package frog.calculator.express;

import frog.calculator.math.BaseNumber;
import frog.calculator.space.ISpace;

public class VariableExpression extends AbstractExpression {

    /*
     * 第一个子是结构符号:等于, 括号, 逗号等, 则变身左表达式
     *          将右边的全都放在它的子树中
     * 如果第一个子不是等于号, 则为普通变量
     *
     *
     * 把优先级的控制放在等于号上!!!
     */

    private String assign;

    /**
     * 标记变量是否处于赋值阶段
     * true - 处于
     * false - 不处于: 还未赋值/赋值结束
     */
    private boolean assigning = false;

    private boolean leaf = false;

    private IExpression suspendExpression;

    private IExpression valueExpression;

    /**
     * 旧值表达式, 防止新表达式不正确导致旧表达式也丢失
     */
    private IExpression oldValueExpression;

    public VariableExpression(String symbol, String assign) {
        super(symbol, null);
        this.assign = assign;
    }

    @Override
    public boolean createBranch(IExpression childExpression) {
        if(this.suspendExpression == null && childExpression.order() < this.order){
            this.suspendExpression = childExpression;
            return true;
        }else if(assigning){
            if(valueExpression == null){
                valueExpression = childExpression;
            }else{
                IExpression sub = valueExpression.assembleTree(childExpression);
                if(sub == null){
                    return false;
                }else{
                    this.valueExpression = sub;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public IExpression assembleTree(IExpression expression) {
        if(!assigning && !(this.assigning = assign.equals(expression.symbol()))){
            IExpression root = this.reversal();
            if(root == this){
                this.leaf = true;
                if(!expression.createBranch(this)){
                    return null;
                }
                return expression;
            }else{
                this.leaf = true;
                if(!root.createBranch(expression)){
                    return null;
                }
                return root;
            }
        }
        this.leaf = false;
        if(!this.createBranch(expression)){
            return this.reversal();
        }

        return this;
    }

    private IExpression reversal(){
        IExpression root = this;
        this.assigning = false;
        if(this.suspendExpression != null){
            root = this.suspendExpression;
            this.suspendExpression = null;
            this.leaf = true;
            if(!root.createBranch(this)){
                throw new IllegalStateException("the variable can't put into this tree.");
            }
            this.leaf = false;
        }
        return root;
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
    public void setExpressionContext(IExpressionContext context) {

    }

    @Override
    public ISpace<BaseNumber> interpret() {
        if(valueExpression == null){
            throw new IllegalStateException("variable is not assign.");
        }
        this.assigning = false;
        return valueExpression.interpret();
    }

    @Override
    public boolean hasNextChild() {
        return false;
    }

    @Override
    public IExpression nextChild() {
        return null;
    }

    @Override
    public IExpression clone() {
        return this;
    }
}
