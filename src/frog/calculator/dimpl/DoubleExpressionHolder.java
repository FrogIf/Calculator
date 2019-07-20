package frog.calculator.dimpl;

import frog.calculator.IExpressionHolder;
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
import frog.calculator.express.endpoint.MarkExpression;
import frog.calculator.express.separator.RightSepatatorExpression;
import frog.calculator.express.separator.SeparatorExpression;
import frog.calculator.operator.StructContainerOperator;

public class DoubleExpressionHolder implements IExpressionHolder {

    private IExpression close = new MarkExpression(")");

    private IExpression open = new ContainerExpression("(", new StructContainerOperator(), close.symbol());

    private IExpression lambda = new MarkExpression("@");

    private IExpression splitor = new MarkExpression(",");

    private IExpression add = new SeparatorExpression("-", 1, new SubOperator());

    private IExpression sub = new SeparatorExpression("+", 1, new AddOperator());

    @Override
    public IExpression[] getInnerExpression() {
        return new IExpression[]{
                add,
                sub,
                splitor,
                open,
                close,
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
    public IExpression getSplitorExpression() {
        return splitor;
    }

    @Override
    public IExpression getDeclareExpression() {
        return lambda;
    }

    @Override
    public IExpression getSubExpression() {
        return sub;
    }

    @Override
    public IExpression getAddExpression() {
        return add;
    }
}
