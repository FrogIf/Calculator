package frog.calculator.express.function;

import frog.calculator.express.ANaturalExpression;
import frog.calculator.express.IExpressContext;
import frog.calculator.express.IExpression;
import frog.calculator.express.result.AResultExpression;
import frog.calculator.operater.IOperator;

/**
 * 变量表达式
 */
public class VariableExpression extends ANaturalExpression {

    private IExpression body;

    private boolean isClose = false;

    /**
     * 用于作为变量名的标记, 目前设计只能是数字
     */
    private IExpression mark;

    public VariableExpression(IOperator operator, String symbol, IExpressContext context) {
        super(operator, context.getMaxPriority(), symbol);
    }

    public void setMark(IExpression mark){
        this.mark = mark;
    }

    public String getMark(){
        AResultExpression result = this.mark.interpret();
        return result.resultValue();
    }

    @Override
    public boolean createBranch(IExpression expression) {
        if(!this.isClose){
            this.isClose = body.createBranch(expression);
        }
        return this.isClose;
    }

    @Override
    public AResultExpression interpret() {
        if(body == null){
            throw new IllegalStateException("variable expression had not assigned.");
        }
        return body.interpret();
    }
}
