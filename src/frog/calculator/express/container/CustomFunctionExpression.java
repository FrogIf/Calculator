package frog.calculator.express.container;

import frog.calculator.express.IExpression;
import frog.calculator.express.IExpressionContext;
import frog.calculator.express.endpoint.VariableExpression;
import frog.calculator.util.collection.Iterator;
import frog.calculator.util.collection.LinkedList;

/**
 * 自定义函数
 */
public class CustomFunctionExpression extends FunctionExpression {

    private IExpression funBody;

    private LinkedList<FormatArgument> args = new LinkedList<>();

    private Iterator<FormatArgument> injectItr = null;

    private FormatArgument currentArg = null;

    private boolean hasDelegate = false;

    /**
     * 创建一个内置函数表达式
     * 一个内置函数结构由以下几部分组成:
     * 容器起始 [参数1 分割符 参数2 分隔符 ... ] 容器终止
     *
     * @param openSymbol  容器起始位置
     * @param splitSymbol 参数分割符
     * @param closeSymbol 容器终止位置
     */
    public CustomFunctionExpression(String openSymbol, String splitSymbol, String closeSymbol) {
        super(openSymbol, null, closeSymbol, splitSymbol);
    }

    @Override
    public boolean buildContent(IExpression childExpression) {
        if(hasDelegate){
            if(this.args.isEmpty()){
                return false;
            }else{
                if(this.splitSymbol.equals(childExpression.symbol())){
                    if(!this.injectItr.hasNext()){ return false; }
                    else{
                        this.currentArg = this.injectItr.next();
                    }
                }else{
                    if(currentArg == null){
                        if(this.injectItr == null) this.injectItr = this.args.iterator();

                        if(!this.injectItr.hasNext()){ return false; }
                        else{
                            this.currentArg = this.injectItr.next();
                        }
                    }

                    this.currentArg.assign(childExpression);
                }
            }
        }else{
            // 形参构造
            if(!this.splitSymbol.equals(childExpression.symbol())){
                this.args.add(new FormatArgument(childExpression.symbol()));
            }
        }
        return true;
    }

    public void delegate(IExpression expression){
        this.hasDelegate = true;
        if(expression == null){
            throw new IllegalArgumentException("function body is empty.");
        }
        this.isClose = false;
        this.funBody = expression;
    }

    /**
     * 调用函数
     * @param expression
     * @return
     */
    @Override
    public IExpression call(IExpression[] expression){
        if(this.funBody == null){
            throw new IllegalArgumentException("function body is empty.");
        }

        IExpressionContext context = this.context.newInstance();

        if(!this.args.isEmpty()){
            Iterator<FormatArgument> iterator = this.args.iterator();
            int i = 0;
            while(iterator.hasNext()){
                FormatArgument next = iterator.next();
                VariableExpression variableExpression = new VariableExpression(next.symbol);    // TODO 需要解耦
                if(next.argExp != null) variableExpression.assign(expression[i]);
                context.addLocalVariable(variableExpression);
                i++;
            }
        }

        this.funBody.setExpressionContext(context);

        return this.funBody.interpret();
    }

    @Override
    public IExpression interpret() {
        if(this.funBody == null){
            throw new IllegalArgumentException("function body is empty.");
        }

        IExpressionContext context = this.context.newInstance();

        if(!this.args.isEmpty()){
            Iterator<FormatArgument> iterator = this.args.iterator();
            while(iterator.hasNext()){
                FormatArgument next = iterator.next();
                VariableExpression variableExpression = new VariableExpression(next.symbol);    // TODO 需要解耦
                if(next.argExp != null) variableExpression.assign(next.argExp);
                context.addLocalVariable(variableExpression);
            }
        }

        this.funBody.setExpressionContext(context);

        return this.funBody.interpret();
    }

    @Override
    public IExpression clone() {
        CustomFunctionExpression clone = (CustomFunctionExpression) super.clone();
        if(!this.args.isEmpty()){
            clone.args = new LinkedList<>();
            Iterator<FormatArgument> iterator = this.args.iterator();
            while (iterator.hasNext()){
                clone.args.add(new FormatArgument(iterator.next().symbol));
            }
        }
        return clone;
    }

    private static class FormatArgument {
        private String symbol;
        private IExpression argExp;
        private FormatArgument(String symbol){
            this.symbol = symbol;
        }
        private void assign(IExpression childExpression){
            if(argExp == null){
                argExp = childExpression;
            }else{
                argExp = argExp.assembleTree(childExpression);
                if(argExp == null){
                    throw new IllegalArgumentException("lost root on argument assign.");
                }
            }
        }
    }

}
