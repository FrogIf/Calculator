package frog.calculator.express.container;

import frog.calculator.express.AbstractExpression;
import frog.calculator.express.IExpression;
import frog.calculator.express.IExpressionContext;
import frog.calculator.express.endpoint.MarkExpression;
import frog.calculator.operator.IOperator;

public class ContainerExpression extends AbstractExpression {

    private IExpression body;

    protected IExpression suspendExpression;

    protected String closeSymbol;

    protected boolean isClose = false;

    /**
     * 容器表达式
     * @param openSymbol 容器起始位置
     * @param operator 容器运算器
     * @param closeSymbol 容器终止位置
     */
    public ContainerExpression(String openSymbol, IOperator operator, String closeSymbol) {
        super(openSymbol, operator);
        if(closeSymbol == null){
            throw new IllegalArgumentException("closeSymbol can not be null.");
        }
        this.closeSymbol = closeSymbol;
    }

    protected boolean buildContent(IExpression childExpression){
        if(this.body == null){
            this.body = childExpression;
        }else{
            IExpression root = this.body.assembleTree(childExpression);
            if(root == null){
                return false;
            }else{
                this.body = root;
            }
        }
        return true;
    }

    @Override
    public final boolean createBranch(IExpression childExpression) {
        if(isClose){
            return false;
        }else{
            if(this.closeSymbol.equals(childExpression.symbol())){
                throw new IllegalStateException("expression error.");
            }

            if(childExpression.order() < this.order()){
                if(this.suspendExpression == null){
                    this.suspendExpression = childExpression;
                    return true;
                }else{
                    return false;
                }
            }else{
                return this.buildContent(childExpression);
            }
        }
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

    private IExpression reversal(){
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
        return operator.operate(this.symbol(), context, new IExpression[]{body, new MarkExpression(closeSymbol)});
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
        clone.body = this.body == null ? null : this.body.clone();
        return clone;
    }

    @Override
    public void setExpressionContext(IExpressionContext context) {
        this.context = context;
        if(this.body != null) this.body.setExpressionContext(context);
    }


}
