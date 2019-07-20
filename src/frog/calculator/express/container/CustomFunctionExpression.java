package frog.calculator.express.container;

import frog.calculator.express.IExpression;
import frog.calculator.express.IExpressionContext;

/**
 * 自定义函数
 */
public class CustomFunctionExpression extends FunctionExpression {

    private IExpressionContext context;

    private IExpression functionBody;   // 函数体, 函数体在clone时不需要clone, 使用一个没有问题

    public CustomFunctionExpression(String symbol, String closeSymbol, String splitSymbol, IExpression funBody, IExpression[] arguments) {
        super(symbol, null, closeSymbol, splitSymbol);
        this.functionBody = funBody;
        if(arguments != null){
            for(IExpression expression : arguments){
                args.addExpression(expression);
                args.setTailClose();
            }
        }
        args.reset();
    }

    @Override
    public IExpression interpret() {
        if(functionBody == null){
            throw new IllegalStateException("function body is empty.");
        }
        IExpressionContext context = this.context.newInstance();

        // 获取到参数表达式
        IExpression[] expressions = this.getArgumentExpressionArray();
        for(IExpression expression : expressions){
            context.addVariables(expression);
        }

        functionBody.setExpressionContext(context);

        return functionBody.interpret();
    }

    @Override
    public void setExpressionContext(IExpressionContext context) {
        super.setExpressionContext(context);
        this.context = context;
    }
}
