package frog.calculator.operater.oprimpl.strimpl;

import frog.calculator.express.IExpressContext;
import frog.calculator.express.IExpression;
import frog.calculator.express.context.DefaultExpressionContext;
import frog.calculator.express.end.NumberExpression;
import frog.calculator.express.mid.MidExpression;
import frog.calculator.express.right.RightExpression;
import frog.calculator.express.round.RoundCloseExpression;
import frog.calculator.express.round.RoundOpenExpression;
import frog.calculator.resolver.IResolverResult;
import frog.calculator.resolver.build.IBuilderPrototypeHolder;
import frog.calculator.resolver.result.DefaultResolveResult;

public class StringBuilderPrototypeHolder implements IBuilderPrototypeHolder {

    private static final IExpressContext context = new DefaultExpressionContext(0, 100);

    @Override
    public NumberExpression getNumberExpressionPrototype() {
        return new NumberExpression(new StringEndOperator(), context);
    }

    @Override
    public IResolverResult getResolverResultPrototype() {
        return new DefaultResolveResult();
    }

    @Override
    public IExpression[] getPrototypeExpressions() {
        return new IExpression[]{
                new MidExpression(new StringMidOperator(), 1, "+"),
                new MidExpression(new StringMidOperator(), 1, "-"),
                new MidExpression(new StringMidOperator(), 2, "*"),
                new MidExpression(new StringMidOperator(), 2, "/"),
                new RightExpression(new StringRightOperator(), 3, "!"),
                new RightExpression(new StringRightOperator(), 3, "%"),
                new RoundOpenExpression(new StringRoundOperator(), "(", ")", context),
                new RoundCloseExpression(new StringRoundOperator(), ")")
        };
    }
}
