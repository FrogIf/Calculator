package frog.calculator.express.end;

import frog.calculator.express.ATerminalExpression;
import frog.calculator.express.ExpressionType;
import frog.calculator.express.IExpression;
import frog.calculator.express.IExpressContext;
import frog.calculator.operater.IOperator;

public class EndExpression extends ATerminalExpression {

    private IExpressContext context;

    public EndExpression(IOperator operator, String symbol, IExpressContext context) {
        super(operator, -1, symbol);
        this.context = context;
    }

    @Override
    public int priority() {
        return context.getMaxPriority();
    }

    @Override
    public boolean createBranch(IExpression expression) {
        return false;
    }

    @Override
    public IExpression assembleTree(IExpression expression){
        if(expression.type() == ExpressionType.TERMINAL){
            // 两个叶子不能组装到一起
            return null;
        }else{
            return expression.assembleTree(this);
        }
    }

    @Override
    public EndExpression clone(){
        return (EndExpression) super.clone();
    }
}
