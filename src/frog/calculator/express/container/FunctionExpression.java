package frog.calculator.express.container;

import frog.calculator.express.IExpression;
import frog.calculator.express.IExpressionContext;
import frog.calculator.operator.IOperator;

/**
 * 内置函数表达式
 */
public class FunctionExpression extends ContainerExpression{

    private String splitSymbol;

    protected ArgumentNode args = new ArgumentNode();

    public FunctionExpression(String symbol, IOperator operator, String closeSymbol, String splitSymbol){
        super(symbol, operator, closeSymbol);
        this.splitSymbol = splitSymbol;
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
                if(splitSymbol.equals(childExpression.symbol())){
                    this.args.setTailClose();
                    return true;
                }
                return this.args.addExpression(childExpression);
            }
        }
        return true;
    }

    @Override
    public IExpression interpret() {
        return this.operator.operate(this.symbol(), getArgumentExpressionArray());
    }

    protected IExpression[] getArgumentExpressionArray(){
        int len = 0;
        if(this.args != null){
            len = this.args.len;
        }
        IExpression[] expressions = new IExpression[len];
        if(len > 0){
            ArgumentNode node = this.args;
            expressions[0] = node.expression;
            node = node.next;

            int i = 1;
            while(node != null){
                expressions[i] = node.expression;
                node = node.next;
                i++;
            }
        }
        return expressions;
    }

    protected static class ArgumentNode{
        private int len = 0;
        protected IExpression expression;
        private ArgumentNode next;
        private boolean isClose;

        private ArgumentNode tail = this;

        protected void reset(){
            tail = this;
            this.isClose = false;
            while(tail.next != null){
                tail.next.isClose = false;
                tail = tail.next;
            }
            tail = this;
        }

        protected void setTailClose(){
            tail.isClose = true;
        }

        protected boolean addExpression(IExpression expression){
            boolean result;
            if(tail.isClose){
                if(tail.next == null) tail.next = new ArgumentNode();
                tail = tail.next;
            }
            if(tail.expression == null){
                tail.expression = expression;
                result = true;
                len++;
            }else{
                IExpression tRoot = tail.expression.assembleTree(expression);
                if(tRoot == null){
                    result = false;
                }else{
                    tail.expression = tRoot;
                    result = true;
                }
            }
            return result;
        }

        protected ArgumentNode copy(){
            ArgumentNode newNode = new ArgumentNode();
            newNode.expression = this.expression == null ? null : this.expression.clone();
            newNode.next = this.next == null ? null : this.next.copy();
            newNode.len = this.len;
            newNode.isClose = this.isClose;
            return newNode;
        }
    }

    @Override
    public void setExpressionContext(IExpressionContext context) {
        IExpression[] expressions = this.getArgumentExpressionArray();
        for(IExpression expression : expressions){
            expression.setExpressionContext(context);
        }
    }

    @Override
    public IExpression clone() {
        FunctionExpression clone = (FunctionExpression) super.clone();
        clone.args = this.args == null ? null : this.args.copy();
        return clone;
    }
}
