package frog.calculator;

import frog.calculator.build.command.*;
import frog.calculator.exception.CalculatorError;
import frog.calculator.exception.DuplicateSymbolException;
import frog.calculator.express.IExpression;
import frog.calculator.express.IExpressionHolder;
import frog.calculator.express.OriginExpressionHolder;
import frog.calculator.build.register.IRegister;
import frog.calculator.build.register.SymbolRegister;
import frog.calculator.build.resolve.DefaultResolverResultFactory;
import frog.calculator.build.resolve.IResolver;
import frog.calculator.build.resolve.IResolverResultFactory;
import frog.calculator.build.resolve.*;

public class DefaultCalculatorComponentFactory implements ICalculatorComponentFactory {

    @Override
    public IResolverResultFactory createResolverResultFactory() {
        return new DefaultResolverResultFactory();
    }

    @Override
    public ICommandHolder createCommandHolder(ICalculatorManager calculatorManager, ICalculatorConfigure configure) {
        return new DefaultCommandHolder(calculatorManager, configure);
    }

    @Override
    public ICalculatorManager createCalculatorManager(ICalculatorConfigure configure) {
        return new DefaultCalculatorManager(configure);
    }

    @Override
    public IResolver createResolver(IExpressionHolder holder, ICalculatorManager manager) {
        // value resolver
        IResolver numberResolver = new NumberResolver(new NumberExpressionFactory(), manager);

        // plus and minus resolver
        // plus and minus can represent (positive and negative) or (add and sub)
        // this resolver can transform like python
        IResolver addSubResolver = new PMResolver(manager, holder.getPlus(), holder.getMinus());

        // symbol resolver, can parse symbol which was supported by framework.
        IResolver symbolResolver = new SymbolResolver(manager,
                createRegister(holder.getBuiltInExpression()));

        // parse execute order : value -> plus and minus -> symbol
        ChainResolver chainResolver = new ChainResolver();
        chainResolver.addResolver(numberResolver);
        chainResolver.addResolver(addSubResolver);
        chainResolver.addResolver(symbolResolver);

        return chainResolver;
    }

    @Override
    public ICommandDetector createCommandDetector(ICommandHolder holder) {
        SymbolRegister<ICommandFactory> register = new SymbolRegister<>();
        ICommandFactory[] commandFactoryList = holder.getCommandFactoryList();
        try {
            for (ICommandFactory commandFactory : commandFactoryList) {
                register.insert(commandFactory);
            }
        } catch (DuplicateSymbolException e) {
            throw new CalculatorError("duplicate system command");
        }
        return new DefaultCommandDetector(register);
    }

    private IExpressionHolder expressionHolder = new OriginExpressionHolder();

    @Override
    public IExpressionHolder createExpressionHolder() {
        return expressionHolder;
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
