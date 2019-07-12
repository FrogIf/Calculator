package frog.calculator.operater.oprimpl.dimpl;

import frog.calculator.operater.oprimpl.dimpl.opr.end.NumberDoubleOperator;
import frog.calculator.operater.oprimpl.dimpl.opr.mid.AddDoubleOperator;
import frog.calculator.operater.oprimpl.dimpl.opr.mid.DivDoubleOperator;
import frog.calculator.operater.oprimpl.dimpl.opr.mid.MultDoubleOperator;
import frog.calculator.operater.oprimpl.dimpl.opr.mid.SubDoubleOperator;
import frog.calculator.operater.oprimpl.dimpl.opr.right.FactorialDoubleOperator;
import frog.calculator.operater.oprimpl.dimpl.opr.right.PercentDoubleOperator;
import frog.calculator.operater.oprimpl.dimpl.opr.round.RoundCloseOperator;
import frog.calculator.operater.oprimpl.dimpl.opr.round.RoundOpenOperator;
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

public class DoubleBuilderPrototypeHolder implements IBuilderPrototypeHolder {

    private static final IExpressContext context = new DefaultExpressionContext(0, 100);

    @Override
    public NumberExpression getNumberExpressionPrototype() {
        return new NumberExpression(new NumberDoubleOperator(), context);
    }

    @Override
    public IResolverResult getResolverResultPrototype() {
        return new DefaultResolveResult();
    }

    @Override
    public IExpression[] getPrototypeExpressions() {
        return new IExpression[]{
                new MidExpression(new AddDoubleOperator(), 1, "+"),
                new MidExpression(new SubDoubleOperator(), 1, "-"),
                new MidExpression(new MultDoubleOperator(), 2, "*"),
                new MidExpression(new DivDoubleOperator(), 2, "/"),
                new RightExpression(new FactorialDoubleOperator(), 3, "!"),
                new RightExpression(new PercentDoubleOperator(), 3, "%"),
                new RoundOpenExpression(new RoundOpenOperator(), "(", ")", context),
                new RoundCloseExpression(new RoundCloseOperator(), ")")
        };
    }
}
