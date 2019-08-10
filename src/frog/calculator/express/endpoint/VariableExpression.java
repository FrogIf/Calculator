package frog.calculator.express.endpoint;

import frog.calculator.express.IExpression;
import frog.calculator.express.IExpressionContext;

public class VariableExpression extends EndPointExpression {

    private IExpression valueExpression;    // 值表达式

    /**
     * 赋值操作符
     * @param variableName  变量名
     */
    public VariableExpression(String variableName) {
        super(variableName, null);
    }

    @Override
    public IExpression interpret() {
        if(this.valueExpression != null){
            return this.valueExpression.interpret();
        }else{
            // 传递过来的是形参, 则需要从变量表中获取值, 需要注意的是, 获取到的值不会赋值给 this.valueExpression
            IExpression localVariable = context.getLocalVariable(this.symbol);
            if(localVariable != null){
                return localVariable.interpret();
            }

            IExpression sessionVariable = context.getSession().getSessionVariable(this.symbol);
            if(sessionVariable == null){
                throw new IllegalArgumentException("undefine variables");
            }

            return sessionVariable.interpret();
        }
    }

    /**
     * 为变量赋值
     * @param expression
     */
    public void assign(IExpression expression){
        this.valueExpression = expression.interpret();
    }

    @Override
    public void setExpressionContext(IExpressionContext context) {
        this.context = context;
        if(this.valueExpression != null){
            this.valueExpression.setExpressionContext(context);
        }
    }

    @Override
    public IExpression clone() {
        return this;
    }
}
