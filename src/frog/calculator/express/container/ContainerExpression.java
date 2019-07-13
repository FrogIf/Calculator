package frog.calculator.express.container;

import frog.calculator.express.AExpression;
import frog.calculator.express.IExpression;
import frog.calculator.express.IExpressionContext;
import frog.calculator.express.endpoint.MarkExpression;
import frog.calculator.operater.IOperator;

public class ContainerExpression extends AExpression {

    private IExpression content;

    private IExpressionContext context;

    private IExpression suspendExpression;

    private String closeSymbol;

    private boolean isClose = false;

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

            if(this.suspendExpression == null){
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
            return super.assembleTree(expression);
        }else{
            if(this.closeSymbol.equals(expression.symbol())){
                isClose = true;
                if(this.suspendExpression != null){
                    root = this.suspendExpression;
                    this.suspendExpression = null;
                    if(!root.createBranch(this)){
                        throw new IllegalStateException("the expression can't put into this tree.");
                    }
                }
            }else{
                if(expression.buildFactor() < 0){
                    if(expression.createBranch(this)){
                        root = expression;
                    }else{
                        root = null;
                    }
                }else if(this.content == null){
                    this.content = expression;
                }else{
                    this.content = this.content.assembleTree(expression);
                    if(this.content == null){
                        throw new IllegalStateException("tree root lost in contain.");
                    }
                }
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
        if(isClose){
            return context.getCurrentMaxBuildFactor();
        }else{
            return 0 - context.getCurrentMaxBuildFactor();
        }
    }

    @Override
    public void setExpressionContext(IExpressionContext context) {
        this.context = context;
    }

    @Override
    public IExpression clone() {
        ContainerExpression clone = (ContainerExpression) super.clone();
        clone.content = this.content == null ? null : this.content.clone();
        return clone;
    }


}
