package frog.calculator;

import frog.calculator.build.DefaultExpressionBuilder;
import frog.calculator.build.IExpressionBuilder;
import frog.calculator.build.command.ICommandDetector;
import frog.calculator.build.command.ICommandHolder;
import frog.calculator.build.resolve.IResolver;
import frog.calculator.build.resolve.IResolverResult;
import frog.calculator.build.resolve.IResolverResultFactory;
import frog.calculator.connect.DefaultCalculatorSession;
import frog.calculator.connect.ICalculatorSession;
import frog.calculator.express.IExpression;
import frog.calculator.express.IExpressionHolder;

public class DefaultCalculatorManager implements ICalculatorManager {

    private final IResolverResultFactory resolverResultFactory;

    // resolve inner symbol
    private IResolver innerResolver;

    // 命令探测器
    private ICommandDetector detector;

    public DefaultCalculatorManager(ICalculatorConfigure configure) {
        ICalculatorComponentFactory componentFactory = configure.getComponentFactory();
        this.resolverResultFactory = componentFactory.createResolverResultFactory();

        IExpressionHolder expressionHolder = componentFactory.createExpressionHolder();
        this.innerResolver = componentFactory.createResolver(expressionHolder, this);

        ICommandHolder commandHolder = componentFactory.createCommandHolder(this, configure);
        this.detector = componentFactory.createCommandDetector(commandHolder);
    }

    @Override
    public IResolverResult createResolverResult(IExpression expression) {
        return resolverResultFactory.createResolverResultBean(expression);
    }

    @Override
    public ICalculatorSession createCalculatorSession() {
        return new DefaultCalculatorSession(this);
    }

    @Override
    public IExpressionBuilder createExpressionBuilder(ICalculatorSession session) {
        return new DefaultExpressionBuilder(session, this, this.innerResolver, this.detector);
    }

    @Override
    public ICalculatorContext createCalculatorContext() {
        return new CommonCalculatorContext();
    }
}
