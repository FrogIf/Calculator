package frog.calculator.express.container;

import frog.calculator.express.IExpression;
import frog.calculator.express.IExpressionContext;
import frog.calculator.express.endpoint.VariableExpression;

/**
 * 自定义函数
 */
public class CustomFunctionExpression extends ContainerExpression{

    private IExpression funBody;

    private FormatArgumentNode formatArgs;

    private String splitSymbol;

    private String delegateSymbol;

    private boolean defined = false;

    private boolean waitNext = true;

    /**
     * 创建一个内置函数表达式
     * 一个内置函数结构由以下几部分组成:
     * 容器起始 [参数1 分割符 参数2 分隔符 ... ] 容器终止
     *
     * @param openSymbol  容器起始位置
     * @param splitSymbol 参数分割符
     * @param closeSymbol 容器终止位置
     * @param delegateSymbol 委托符号
     */
    public CustomFunctionExpression(String openSymbol, String splitSymbol, String closeSymbol, String delegateSymbol) {
        super(openSymbol, null, closeSymbol);
        this.splitSymbol = splitSymbol;
        this.delegateSymbol = delegateSymbol;
    }

    @Override
    public IExpression assembleTree(IExpression expression) {
        if(this.isClose){
            defined = true;
            if(this.formatArgs != null) this.formatArgs.waitActualArgument();

            if(this.delegateSymbol.equals(expression.symbol())){
                if(this.funBody != null){
                    throw new IllegalArgumentException("the function body has assigned.");
                }
                return super.assembleTree(expression);
            }

            if(this.funBody == null){
                this.funBody = expression;
                this.isClose = false;
                return this;
            }else{
                if(createBranch(expression)){
                    return this;
                }else{
                    return null;
                }
            }
        }else{
            return super.assembleTree(expression);
        }
    }

    @Override
    public boolean createBranch(IExpression childExpression) {
        if(defined){
            if(this.order > childExpression.order()){
                if(this.suspendExpression != null){
                    return false;
                }
                this.suspendExpression = childExpression;
                return true;
            }
            // 实参注入
            if(this.formatArgs == null){
                return false;
            }else{
                if(this.splitSymbol.equals(childExpression.symbol())){
                    this.formatArgs.toNext();
                }else{
                    this.formatArgs.assign(childExpression);
                }
            }
            return true;
        }else{
            // 形参构造
            if(this.closeSymbol.equals(childExpression.symbol())){
                throw new IllegalStateException("expression error.");
            }

            if(this.suspendExpression == null && this.order() > childExpression.order()){
                this.suspendExpression = childExpression;
            }else{
                if(this.splitSymbol.equals(childExpression.symbol())){
                    waitNext = true;
                }else if(waitNext){
                    waitNext = false;
                    if(this.formatArgs == null){
                        this.formatArgs = new FormatArgumentNode(childExpression.symbol());
                    }else{
                        this.formatArgs.createNewArgument(childExpression.symbol());
                    }
                }else{
                    return false;
                }
            }
            return true;
        }
    }

    @Override
    public IExpression interpret() {
        if(this.funBody == null){
            throw new IllegalArgumentException("function body is empty.");
        }
        IExpressionContext context = this.context.newInstance();
        if(this.formatArgs != null){
            FormatArgumentNode node = this.formatArgs;
            while(node != null){
                VariableExpression variableExpression = new VariableExpression(node.symbol);    // TODO 需要解耦
                if(node.expression != null) variableExpression.assign(node.expression);
                context.addLocalVariable(variableExpression);
                node = node.next;
            }
        }
        this.funBody.setExpressionContext(context);
        return this.funBody.interpret();
    }

    @Override
    public IExpression clone() {
        CustomFunctionExpression clone = (CustomFunctionExpression) super.clone();
        clone.formatArgs = this.formatArgs == null ? null : this.formatArgs.copy();
        return clone;
    }

    private static class FormatArgumentNode {
        private String symbol;
        private FormatArgumentNode next;
        private FormatArgumentNode tail = this;
        private IExpression expression;

        private FormatArgumentNode(String symbol) {
            this.symbol = symbol;
        }

        private void createNewArgument(String symbol){
            this.tail.next = new FormatArgumentNode(symbol);
            this.tail = this.tail.next;
        }

        private void toNext(){
            this.tail = this.tail.next;
        }

        private void waitActualArgument(){
            this.tail = this;
        }

        private FormatArgumentNode copy(){
            FormatArgumentNode node = new FormatArgumentNode(this.symbol);
            node.next = this.next == null ? null : next.copy();
            node.tail = node;
            return node;
        }

        private void assign(IExpression expression){
            if(this.tail.expression == null){
                this.tail.expression = expression;
            }else{
                IExpression newRoot = tail.expression.assembleTree(expression);
                if(newRoot == null){
                    throw new IllegalArgumentException("lost root on argument assign.");
                }else{
                    this.tail.expression = newRoot;
                }
            }
        }
    }
}
