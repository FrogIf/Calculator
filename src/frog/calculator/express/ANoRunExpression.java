package frog.calculator.express;

import frog.calculator.express.result.AResultExpression;
import frog.calculator.operate.IOperator;

public abstract class ANoRunExpression extends APriorityExpression {

    public ANoRunExpression(IOperator operator, int priority, String symbol) {
        super(operator, priority, symbol);
    }

    @Override
    public boolean createBranch(IExpression expression) {
        return false;
    }

    @Override
    public AResultExpression interpret() {
        throw new UnsupportedOperationException("no run expression unsupported this method.");
    }

    @Override
    public boolean leaf(){
        return true;
    }
}
