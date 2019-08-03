package frog.calculator.express.container;

import frog.calculator.express.IExpression;
import frog.calculator.express.IExpressionContext;
import frog.calculator.express.endpoint.VariableExpression;

/**
 * 自定义函数
 */
public class CustomFunctionExpression extends ContainerExpression{

    private IExpression funBody;    // function body

    private IExpressionContext context;

    private FormatArgumentNode fargs;

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
     * @param closeSymbol 容器终止位置
     * @param splitSymbol 参数分割符
     */
    public CustomFunctionExpression(String openSymbol, String closeSymbol, String splitSymbol, String delegateSymbol) {
        super(openSymbol, null, closeSymbol);
        this.splitSymbol = splitSymbol;
        this.delegateSymbol = delegateSymbol;
    }

    @Override
    public IExpression assembleTree(IExpression expression) {
        if(this.isClose){
            defined = true;
            if(this.fargs != null) this.fargs.back();  // 回到初始位置, 准备实参注入
            // 定义方法体部分
            if(this.delegateSymbol.equals(expression.symbol())){
                if(this.funBody != null){
                    throw new IllegalArgumentException("the function body has assigned.");
                }
                return super.assembleTree(expression);
            }
            if(this.funBody == null){
                this.funBody = expression;  // 指定函数体
                this.isClose = false;   // 使其可以继续createBranch
                return this;
            }else{
                // 接收实参部分
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
            if(this.fargs == null){
                return false;
            }else{
                if(this.splitSymbol.equals(childExpression.symbol())){
                    this.fargs.toNext();
                }else{
                    this.fargs.assign(childExpression);
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
                    if(this.fargs == null){
                        this.fargs = new FormatArgumentNode(childExpression.symbol());
                    }else{
                        this.fargs.createNewArgument(childExpression.symbol());
                    }
                }else{
                    return false;
                }
            }
            return true;
        }
    }

    @Override
    public void setExpressionContext(IExpressionContext context) {
        this.context = context;
        super.setExpressionContext(context);
    }

    @Override
    public IExpression interpret() {
        if(this.funBody == null){
            throw new IllegalArgumentException("function body is empty.");
        }
        IExpressionContext context = this.context.newInstance();
        if(this.fargs != null){
            FormatArgumentNode node = this.fargs;
            while(node != null){
                VariableExpression variableExpression = new VariableExpression(node.symbol);    // TODO 需要解耦
                if(node.expression != null) variableExpression.assign(node.expression);
                context.addLocalVariables(variableExpression);
                node = node.next;
            }
        }
        this.funBody.setExpressionContext(context);
        return this.funBody.interpret();
    }

    @Override
    public IExpression clone() {
        CustomFunctionExpression clone = (CustomFunctionExpression) super.clone();
        clone.fargs = this.fargs == null ? null : this.fargs.copy();
        return clone;
    }

    private static class FormatArgumentNode {
        private String symbol;
        private FormatArgumentNode next;
        private FormatArgumentNode tail = this;
        private IExpression expression;

        public FormatArgumentNode(String symbol) {
            this.symbol = symbol;
        }

        private void createNewArgument(String symbol){
            tail.next = new FormatArgumentNode(symbol);
            tail = tail.next;
        }
        private void toNext(){
            tail = tail.next;
        }
        private void back(){
            tail = this;
        }

        private FormatArgumentNode copy(){
            FormatArgumentNode node = new FormatArgumentNode(this.symbol);
            node.next = this.next == null ? null : next.copy();
            node.tail = node;
            return node;
        }

        private void assign(IExpression expression){
            if(tail.expression == null){
                tail.expression = expression;
            }else{
                IExpression newRoot = tail.expression.assembleTree(expression);
                if(newRoot == null){
                    throw new IllegalArgumentException("lost root on argument assign.");
                }else{
                    tail.expression = newRoot;
                }
            }
        }
    }
}
