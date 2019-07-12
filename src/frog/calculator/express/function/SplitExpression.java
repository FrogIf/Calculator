package frog.calculator.express.function;

import frog.calculator.express.ACombinedExpression;
import frog.calculator.express.IExpression;
import frog.calculator.operater.IOperator;

/**
 * 分隔符表达式
 */
public class SplitExpression extends ACombinedExpression {

    public SplitExpression(IOperator operator, int priority, String symbol) {
        super(operator, priority, symbol);
    }

    @Override
    public boolean createBranch(IExpression expression) {
        return false;
    }
}
