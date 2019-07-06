package frog.calculator.express.round;

import frog.calculator.express.ANoRunExpression;
import frog.calculator.operate.IOperator;

public class RoundCloseExpression extends ANoRunExpression {

    public RoundCloseExpression(IOperator operator, String symbol) {
        super(operator, -1, symbol);
    }
}
