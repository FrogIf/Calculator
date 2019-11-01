package frog.calculator;

import frog.calculator.command.*;
import frog.calculator.express.IExpression;
import frog.calculator.express.IExpressionHolder;
import frog.calculator.express.OriginExpressionHolder;
import frog.calculator.register.IRegister;
import frog.calculator.register.SymbolRegister;
import frog.calculator.resolver.DefaultResolverResultFactory;
import frog.calculator.resolver.IResolver;
import frog.calculator.resolver.IResolverResultFactory;
import frog.calculator.resolver.resolve.ChainResolver;
import frog.calculator.resolver.resolve.NumberResolver;
import frog.calculator.resolver.resolve.PMResolver;
import frog.calculator.resolver.resolve.SymbolResolver;
import frog.calculator.resolver.resolve.factory.NumberExpressionFactory;

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
        SymbolRegister<ICommand> register = new SymbolRegister<>();
        ICommand[] commands = holder.getCommands();
        for (ICommand command : commands) {
            register.insert(command);
        }
        return new DefaultCommandDetector(register);
    }

    @Override
    public IExpressionHolder createExpressionHolder() {
        return new OriginExpressionHolder();
    }

    private IRegister<IExpression> createRegister(IExpression[] expressions) {
        SymbolRegister<IExpression> register = new SymbolRegister<>();
        for (IExpression exp : expressions) {
            register.insert(exp);
        }
        return register;
    }

}
