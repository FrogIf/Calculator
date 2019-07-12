package frog.calculator.express.round;

import frog.calculator.express.ACombinedExpression;
import frog.calculator.express.IExpression;
import frog.calculator.operater.IOperator;

public class RoundCloseExpression extends ACombinedExpression {

    public RoundCloseExpression(IOperator operator, String symbol) {
        super(operator, -1, symbol);
    }

    @Override
    public boolean createBranch(IExpression expression) {
        return false;
    }
}
