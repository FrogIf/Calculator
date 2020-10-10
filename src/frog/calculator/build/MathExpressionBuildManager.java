package frog.calculator.build;

import frog.calculator.build.command.*;
import frog.calculator.build.register.IRegister;
import frog.calculator.build.register.SymbolRegister;
import frog.calculator.build.resolve.*;
import frog.calculator.exception.CalculatorError;
import frog.calculator.exception.DuplicateSymbolException;
import frog.calculator.express.IExpression;
import frog.calculator.express.holder.IExpressionHolder;
import frog.calculator.express.holder.MathExpressionHolder;

/**
 * 默认使用的解释管理器
 */
public class MathExpressionBuildManager implements IBuildManager {

    private final ICommandHolder commandHolder = new DefaultCommandHolder();

    private final IExpressionHolder expressionHolder = new MathExpressionHolder();

    private final IResolver resolver;

    private final ICommandDetector detector;

    public MathExpressionBuildManager() {
        this.resolver = this.initResolver();
        this.detector = this.initCommandDetector();
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

    private IResolver initResolver() {
        // value resolver
        IResolver numberResolver = new NumberResolver();

        // plus and minus resolver
        // plus and minus can represent (positive and negative) or (add and sub)
        // this resolver can transform like python
        IExpression plusExp = expressionHolder.getExpressionBySymbol(MathExpressionHolder.PLUS);
        IExpression minusExp = expressionHolder.getExpressionBySymbol(MathExpressionHolder.MINUS);
        IResolver addSubResolver = new PMResolver(plusExp, minusExp);

        // symbol resolver, can parse symbol which was supported by framework.
        IResolver symbolResolver = new SymbolResolver(createRegister(this.expressionHolder.getExpressions()));

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
