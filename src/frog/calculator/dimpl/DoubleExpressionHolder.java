package frog.calculator.dimpl;

import frog.calculator.AbstractExpressionHolder;
import frog.calculator.dimpl.opr.fun.AvgOperator;
import frog.calculator.dimpl.opr.fun.MaxOperator;
import frog.calculator.dimpl.opr.fun.SigmaOperator;
import frog.calculator.dimpl.opr.fun.SqrtOperator;
import frog.calculator.dimpl.opr.single.FactorialOperator;
import frog.calculator.dimpl.opr.single.PercentOperator;
import frog.calculator.dimpl.opr.two.DivOperator;
import frog.calculator.dimpl.opr.two.MultiOperator;
import frog.calculator.express.IExpression;
import frog.calculator.express.container.ContainerExpression;
import frog.calculator.express.container.FunctionExpression;
import frog.calculator.express.separator.AdjacencyLeftExpression;
import frog.calculator.express.separator.RightSeparatorExpression;
import frog.calculator.express.separator.SeparatorExpression;

public class DoubleExpressionHolder extends AbstractExpressionHolder {

    @Override
    protected IExpression[] getRunnableExpression() {
        return new IExpression[]{
                new SeparatorExpression("*", 2, new MultiOperator()),
                new SeparatorExpression("/", 2, new DivOperator()),
                new RightSeparatorExpression("!", 3, new FactorialOperator()),
                new RightSeparatorExpression("%", 3, new PercentOperator()),
                new ContainerExpression("sqrt(", new SqrtOperator(), ")"),
                new FunctionExpression("max(", new MaxOperator(), ")", ","),
                new AdjacencyLeftExpression("avg", new AvgOperator()),
                new FunctionExpression("sigma(", new SigmaOperator(), ")", ",")
        };
    }
}
