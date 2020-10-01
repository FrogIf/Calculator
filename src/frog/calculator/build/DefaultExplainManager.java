package frog.calculator.build;

import frog.calculator.build.command.*;
import frog.calculator.build.register.IRegister;
import frog.calculator.build.register.SymbolRegister;
import frog.calculator.build.resolve.*;
import frog.calculator.exception.CalculatorError;
import frog.calculator.exception.DuplicateSymbolException;
import frog.calculator.execute.holder.IExpressionHolder;
import frog.calculator.execute.holder.OriginExpressionHolder;
import frog.calculator.express.IExpression;

/**
 * 默认使用的解释管理器
 */
public class DefaultExplainManager implements IExplainManager {

    private ICommandHolder commandHolder = new DefaultCommandHolder(this);

    private IExpressionHolder expressionHolder = new OriginExpressionHolder();

    private IResolverResultFactory resolverResultFactory = new DefaultResolverResultFactory();

    private IResolver resolver;

    private ICommandDetector detector;

    public DefaultExplainManager() {
        this.resolver = this.initResolver();
        this.detector = this.initCommandDetector();
    }

    @Override
    public IResolverResult assembleResolveResult(IExpression expression) {
        return resolverResultFactory.createResolverResultBean(expression);
    }

    @Override
    public ICommandHolder getCommandHolder() {
        return this.commandHolder;
    }

    @Override
    public IResolver getResolver() {
        return this.resolver;
    }

    @Override
    public ICommandDetector getCommandDetector() {
        return this.detector;
    }

    private ICommandDetector initCommandDetector(){
        SymbolRegister<ICommandFactory> register = new SymbolRegister<>();
        ICommandFactory[] commandFactoryList = this.commandHolder.getCommandFactoryList();
        try {
            for (ICommandFactory commandFactory : commandFactoryList) {
                register.insert(commandFactory);
            }
        } catch (DuplicateSymbolException e) {
            throw new CalculatorError("duplicate system command");
        }
        return new DefaultCommandDetector(register);
    }

    @Override
    public IExpressionHolder getExpressionHolder() {
        return this.expressionHolder;
    }

    @Override
    public IResolverResultFactory getResolverFactory() {
        return this.resolverResultFactory;
    }


    private IResolver initResolver() {
        // value resolver
        IResolver numberResolver = new NumberResolver(new NumberExpressionFactory(), resolverResultFactory);

        // plus and minus resolver
        // plus and minus can represent (positive and negative) or (add and sub)
        // this resolver can transform like python
        IResolver addSubResolver = new PMResolver(this.expressionHolder.getPlus(), this.expressionHolder.getMinus(), resolverResultFactory);

        // symbol resolver, can parse symbol which was supported by framework.
        IResolver symbolResolver = new SymbolResolver(createRegister(this.expressionHolder.getBuiltInExpression()), resolverResultFactory);

        // parse execute order : value -> plus and minus -> symbol
        ChainResolver chainResolver = new ChainResolver();
        chainResolver.addResolver(numberResolver);
        chainResolver.addResolver(addSubResolver);
        chainResolver.addResolver(symbolResolver);

        return chainResolver;
    }

    private IRegister<IExpression> createRegister(IExpression[] expressions) {
        SymbolRegister<IExpression> register = new SymbolRegister<>();
        for (IExpression exp : expressions) {
            try {
                register.insert(exp);
            } catch (DuplicateSymbolException e) {
                throw new CalculatorError("duplicate system expression");
            }
        }
        return register;
    }
}
