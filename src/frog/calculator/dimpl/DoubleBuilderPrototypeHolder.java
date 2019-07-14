package frog.calculator.dimpl;

import frog.calculator.express.IExpression;
import frog.calculator.express.container.ContainerExpression;
import frog.calculator.express.endpoint.MarkExpression;
import frog.calculator.express.separator.RightSepatatorExpression;
import frog.calculator.express.separator.SeparatorExpression;
import frog.calculator.operater.StructContainerOperator;
import frog.calculator.dimpl.opr.SqrtOperator;
import frog.calculator.dimpl.opr.single.FactorialOperator;
import frog.calculator.dimpl.opr.single.PercentOperator;
import frog.calculator.dimpl.opr.two.AddOperator;
import frog.calculator.dimpl.opr.two.DivOperator;
import frog.calculator.dimpl.opr.two.MultiOperator;
import frog.calculator.dimpl.opr.two.SubOperator;
import frog.calculator.resolver.IResolverResult;
import frog.calculator.resolver.build.DefaultNumberExpressionFactory;
import frog.calculator.resolver.build.IBuilderPrototypeHolder;
import frog.calculator.resolver.build.INumberExpressionFactory;
import frog.calculator.resolver.result.DefaultResolveResult;

public class DoubleBuilderPrototypeHolder implements IBuilderPrototypeHolder {

    @Override
    public INumberExpressionFactory getNumberExpressionFactory() {
        return new DefaultNumberExpressionFactory();
    }

    @Override
    public IResolverResult getResolverResultPrototype() {
        return new DefaultResolveResult();
    }

    @Override
    public IExpression[] getPrototypeExpressions() {
        return new IExpression[]{
                new SeparatorExpression("*", 2, new MultiOperator()),
                new SeparatorExpression("/", 2, new DivOperator()),
                new RightSepatatorExpression("!", 3, new FactorialOperator()),
                new RightSepatatorExpression("%", 3, new PercentOperator()),
                new ContainerExpression("(", new StructContainerOperator(), ")"),
                new MarkExpression(")"),
                new ContainerExpression("sqrt(", new SqrtOperator(), ")")
        };
    }

    @Override
    public IExpression getAddExpressionPrototype() {
        return new SeparatorExpression("+", 1, new AddOperator());
    }

    @Override
    public IExpression getSubExpressionPrototype() {
        return new SeparatorExpression("-", 1, new SubOperator());
    }
}
