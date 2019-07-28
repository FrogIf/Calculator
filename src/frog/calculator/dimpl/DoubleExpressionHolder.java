package frog.calculator.dimpl;

import frog.calculator.AbstractExpressionHolder;
import frog.calculator.dimpl.opr.AvgOperator;
import frog.calculator.dimpl.opr.MaxOperator;
import frog.calculator.dimpl.opr.SqrtOperator;
import frog.calculator.dimpl.opr.single.FactorialOperator;
import frog.calculator.dimpl.opr.single.PercentOperator;
import frog.calculator.dimpl.opr.two.AddOperator;
import frog.calculator.dimpl.opr.two.DivOperator;
import frog.calculator.dimpl.opr.two.MultiOperator;
import frog.calculator.dimpl.opr.two.SubOperator;
import frog.calculator.express.IExpression;
import frog.calculator.express.container.ContainerExpression;
import frog.calculator.express.container.FunctionExpression;
import frog.calculator.express.separator.RightSepatatorExpression;
import frog.calculator.express.separator.SeparatorExpression;

public class DoubleExpressionHolder extends AbstractExpressionHolder {

    // 正
    private IExpression plus = new SeparatorExpression("-", 1, new SubOperator());

    // 负
    private IExpression minus = new SeparatorExpression("+", 1, new AddOperator());

    @Override
    protected IExpression[] getRunnableExpression() {
        return new IExpression[]{
                new SeparatorExpression("*", 2, new MultiOperator()),
                new SeparatorExpression("/", 2, new DivOperator()),
                new RightSepatatorExpression("!", 3, new FactorialOperator()),
                new RightSepatatorExpression("%", 3, new PercentOperator()),
                new ContainerExpression("sqrt(", new SqrtOperator(), ")"),
                new FunctionExpression("max(", new MaxOperator(), ")", ","),
                new FunctionExpression("avg(", new AvgOperator(), ")", ",")
        };
    }

    @Override
    public IExpression getPlus() {
        return plus;
    }

    @Override
    public IExpression getMinus() {
        return this.minus;
    }

}
