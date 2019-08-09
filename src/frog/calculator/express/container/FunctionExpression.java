package frog.calculator.express.container;

import frog.calculator.express.IExpression;
import frog.calculator.express.IExpressionContext;
import frog.calculator.operator.IOperator;

/**
 * 内置函数表达式
 */
public class FunctionExpression extends ContainerExpression{

    private String splitSymbol;

    private int argumentCount = 0;

    private ArgumentNode args = null;

    /**
     * 创建一个内置函数表达式
     * 一个内置函数结构由以下几部分组成:
     *  容器起始 [参数1 分割符 参数2 分隔符 ... ] 容器终止
     * @param openSymbol 容器起始位置
     * @param operator  函数实际算法
     * @param closeSymbol 容器终止位置
     * @param splitSymbol 参数分割符
     */
    public FunctionExpression(String openSymbol, IOperator operator, String closeSymbol, String splitSymbol){
        super(openSymbol, operator, closeSymbol);
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
                    argumentCount++;
                    args.createNewNode();
                }else{
                    if(args == null){
                        argumentCount++;
                        args = new ArgumentNode();
                    }
                    args.addToCurrentNode(childExpression);
                }
            }
        }
        return true;
    }

    @Override
    public IExpression interpret() {
        if(this.argumentCount > 0){
            ArgumentNode node = this.args;
            IExpression[] expressions = new IExpression[this.argumentCount];

            int i = 0;
            while(node != null){
                expressions[i] = node.expression;
                node = node.next;
                i++;
            }

            return this.operator.operate(this.symbol(), context, expressions);
        }else{
            return this.operator.operate(this.symbol(), context, null);
        }
    }

    @Override
    public void setExpressionContext(IExpressionContext context) {
        this.context = context;
        if(this.args != null){
            ArgumentNode node = this.args;
            while(node != null){
                if(node.expression != null){
                    node.expression.setExpressionContext(context);
                }
                node = node.next;
            }
        }
    }

    @Override
    public IExpression clone() {
        FunctionExpression clone = (FunctionExpression) super.clone();
        clone.args = this.args == null ? null : this.args.copy();
        return clone;
    }

    private static class ArgumentNode {
        IExpression expression;

        ArgumentNode next;
        ArgumentNode tail = this;

        private void createNewNode(){
            tail.next = new ArgumentNode();
            tail = tail.next;
        }

        private void addToCurrentNode(IExpression childExpression) {
            if(tail.expression == null){
                tail.expression = childExpression;
            }else{
                IExpression expression = tail.expression.assembleTree(childExpression);
                if(expression == null){
                    throw new IllegalArgumentException("expression can't assemble.");
                }else{
                    tail.expression = expression;
                }
            }
        }

        private ArgumentNode copy(){
            ArgumentNode newNode = new ArgumentNode();
            newNode.expression = this.expression == null ? null : this.expression.clone();
            newNode.next = this.next == null ? null : this.next.copy();
            return newNode;
        }
    }
}
