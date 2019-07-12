package frog.calculator.operater.oprimpl.stepimpl;

import frog.calculator.operater.oprimpl.dimpl.opr.end.NumberDoubleOperator;
import frog.calculator.operater.oprimpl.dimpl.opr.mid.AddDoubleOperator;
import frog.calculator.operater.oprimpl.dimpl.opr.mid.DivDoubleOperator;
import frog.calculator.operater.oprimpl.dimpl.opr.mid.MultDoubleOperator;
import frog.calculator.operater.oprimpl.dimpl.opr.mid.SubDoubleOperator;
import frog.calculator.operater.oprimpl.dimpl.opr.right.FactorialDoubleOperator;
import frog.calculator.operater.oprimpl.dimpl.opr.right.PercentDoubleOperator;
import frog.calculator.operater.oprimpl.dimpl.opr.round.RoundCloseOperator;
import frog.calculator.operater.oprimpl.dimpl.opr.round.RoundOpenOperator;
import frog.calculator.operater.oprimpl.strimpl.StringEndOperator;
import frog.calculator.operater.oprimpl.strimpl.StringMidOperator;
import frog.calculator.operater.oprimpl.strimpl.StringRightOperator;
import frog.calculator.operater.oprimpl.strimpl.StringRoundOperator;
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

public class StepBuilderPrototypeHolder implements IBuilderPrototypeHolder {

    private static final IExpressContext context = new DefaultExpressionContext(0, 100);

    private static final StepMonitorContext stepContext = new StepMonitorContext();

    @Override
    public NumberExpression getNumberExpressionPrototype() {
        return new NumberExpression(
                new StepOperatorWrapper(new NumberDoubleOperator(), new StringEndOperator(), stepContext),
                context
        );
    }

    @Override
    public IResolverResult getResolverResultPrototype() {
        return new DefaultResolveResult();
    }

    @Override
    public IExpression[] getPrototypeExpressions() {
        return new IExpression[]{
                new MidExpression(
                        new StepOperatorWrapper(new AddDoubleOperator(), new StringMidOperator(), stepContext),
                        1,
                        "+"),
                new MidExpression(new StepOperatorWrapper(new SubDoubleOperator(), new StringMidOperator(), stepContext),
                        1,
                        "-"),
                new MidExpression(new StepOperatorWrapper(new MultDoubleOperator(), new StringMidOperator(), stepContext),
                        2,
                        "*"),
                new MidExpression(new StepOperatorWrapper(new DivDoubleOperator(), new StringMidOperator(), stepContext),
                        2,
                        "/"),
                new RightExpression(new StepOperatorWrapper(new FactorialDoubleOperator(), new StringRightOperator(), stepContext),
                        3,
                        "!"),
                new RightExpression(new StepOperatorWrapper(new PercentDoubleOperator(), new StringRightOperator(), stepContext),
                        3,
                        "%"),
                new RoundOpenExpression(new StepOperatorWrapper(new RoundOpenOperator(), new StringRoundOperator(), stepContext),
                        "(",
                        ")",
                        context),
                new RoundCloseExpression(new StepOperatorWrapper(new RoundCloseOperator(), new StringRoundOperator(), stepContext),
                        ")")
        };
    }
}
