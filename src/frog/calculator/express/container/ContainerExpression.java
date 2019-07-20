package frog.calculator.express.container;

import frog.calculator.express.AbstractExpression;
import frog.calculator.express.IExpression;
import frog.calculator.express.IExpressionContext;
import frog.calculator.express.endpoint.MarkExpression;
import frog.calculator.operator.IOperator;

public class ContainerExpression extends AbstractExpression {

    private IExpression content;

    protected IExpression suspendExpression;

    protected String closeSymbol;

    protected boolean isClose = false;

    public ContainerExpression(String symbol, IOperator operator, String closeSymbol) {
        super(symbol, operator);
        if(closeSymbol == null){
            throw new IllegalArgumentException("closeSymbol can not be null.");
        }
        this.closeSymbol = closeSymbol;
    }

    @Override
    public boolean createBranch(IExpression childExpression) {
        if(isClose){
            return false;
        }else{
            if(this.closeSymbol.equals(childExpression.symbol())){
                throw new IllegalStateException("expression error.");
            }

            if(this.suspendExpression == null && this.order() > childExpression.order()){
                this.suspendExpression = childExpression;
            }else{
                if(this.content == null){
                    this.content = childExpression;
                }else{
                    IExpression root = this.content.assembleTree(childExpression);
                    if(root == null){
                        return false;
                    }else{
                        this.content = root;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public IExpression assembleTree(IExpression expression) {
        IExpression root = this;

        if(isClose){
            if(expression.createBranch(this)){
                return expression;
            }else{
                return null;
            }
        }else{
            if(this.closeSymbol.equals(expression.symbol())){
                root = reversal();
            }else{
                if(expression.buildFactor() < 0){
                    if(expression.createBranch(this)){
                        root = expression;
                    }else{
                        root = null;
                    }
                }else{
                    if(!this.createBranch(expression)){
                        root = null;
                    }
                }
            }
        }

        return root;
    }

    protected IExpression reversal(){
        IExpression root = this;
        isClose = true;
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
    public IExpression interpret() {
        return operator.operate(this.symbol(), content, new MarkExpression(closeSymbol));
    }

    @Override
    public boolean isLeaf() {
        return isClose;
    }

    @Override
    public int buildFactor() {
        return -1;
    }

    @Override
    public IExpression clone() {
        ContainerExpression clone = (ContainerExpression) super.clone();
        clone.content = this.content == null ? null : this.content.clone();
        return clone;
    }

    @Override
    public void setExpressionContext(IExpressionContext context) {
        if(this.content != null) this.content.setExpressionContext(context);
    }


}
