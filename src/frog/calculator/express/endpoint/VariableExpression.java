package frog.calculator.express.endpoint;

import frog.calculator.express.IExpression;
import frog.calculator.express.IExpressionContext;
import frog.calculator.util.LinkedList;

public class VariableExpression extends EndPointExpression {

    private IExpression valueExpression;    // 值表达式

    private IExpressionContext context;     // 表达式上下文

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
            IExpression result = null;

            LinkedList<IExpression> variables = context.getLocalVariables();
            LinkedList<IExpression>.Iterator iterator = variables.getIterator();

            while(iterator.hasNext()){
                IExpression variable = iterator.next();
                if(variable.symbol().equals(this.symbol)){
                    result = variable.interpret();
                    break;
                }
            }

            if(result == null) {
                result = context.getSession().getUserRegister().find(this.symbol());
            }

            if(result == null){
                throw new IllegalArgumentException("undefine variables");
            }
            return result.interpret();
        }
    }

//    @Override
//    public IExpression assembleTree(IExpression expression) {
//        this.valueExpression = expression;
//        return this;
//    }

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
        VariableExpression expression = (VariableExpression) super.clone();
        expression.valueExpression = this.valueExpression == null ? null : this.valueExpression.clone();
        return expression;
    }
}
