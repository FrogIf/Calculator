package frog.calculator.express.end;

import frog.calculator.express.IExpression;
import frog.calculator.express.APriorityExpression;
import frog.calculator.express.context.IExpressContext;
import frog.calculator.operate.IOperator;

public class EndExpression extends APriorityExpression {

    private IExpressContext context;

    public EndExpression(IOperator operator, String symbol, IExpressContext context) {
        super(operator, symbol);
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
    public boolean leaf(){
        return true;
    }

    @Override
    public IExpression assembleTree(IExpression expression){
        if(expression.leaf()){
            // 两个叶子不能组装到一起
            return null;
        }else{
            return expression.assembleTree(this);
        }
    }
}
