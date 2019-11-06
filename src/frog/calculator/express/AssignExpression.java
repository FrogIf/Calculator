package frog.calculator.express;

import frog.calculator.math.BaseNumber;
import frog.calculator.space.ISpace;

/**
 * 赋值表达式
 */
public class AssignExpression extends AbstractExpression{

    private IExpression suspendExpression;

    private IExpression valueExpression;

    private String end;

    private boolean leaf = false;

    public AssignExpression(String symbol, String end) {
        super(symbol, null);
        this.end = end;
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
                throw new IllegalStateException("the expression can't put into this tree.");
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
            }else if(this.end.equals(expression.symbol())){
                this.leaf = true;
                root = this.reversal();
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
    public boolean hasNextChild() {
        return false;
    }

    @Override
    public IExpression nextChild() {
        return null;
    }


    @Override
    public ISpace<BaseNumber> interpret() {
        if(this.valueExpression == null){
            throw new IllegalStateException("assign is empty.");
        }
        return this.valueExpression.interpret();
    }

    @Override
    public IExpression clone() {
        return new AssignExpression(this.symbol, this.end);
    }
}