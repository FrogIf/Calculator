package frog.calculator.dimpl;

import frog.calculator.AbstractExpressionHolder;
import frog.calculator.express.IExpression;
import frog.calculator.operator.IOperator;

public class DoubleExpressionHolder extends AbstractExpressionHolder {

    @Override
    protected IExpression[] getRunnableExpression() {
        return new IExpression[]{
//                new SeparatorExpression("*", 2, new MultiOperator()),
//                new SeparatorExpression("/", 2, new DivOperator()),
//                new RightSeparatorExpression("!", 3, new FactorialOperator()),
//                new RightSeparatorExpression("%", 3, new PercentOperator()),
//                new ContainerExpression("sqrt(", new SqrtOperator(), ")"),
//                new FunctionExpression("max(", new MaxOperator(), ")", ","),
//                new AdjacencyLeftExpression("avg", new AvgOperator()),
//                new FunctionExpression("sigma(", new SigmaOperator(), ")", ",")
        };
    }

    @Override
    public IExpression getPlus() {
        return null;
    }

    @Override
    public IExpression getMinus() {
        return null;
    }

    @Override
    public IOperator getNumberOperator() {
        return null;
    }
}
