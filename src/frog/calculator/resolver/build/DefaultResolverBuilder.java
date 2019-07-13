package frog.calculator.resolver.build;

import frog.calculator.express.IExpression;
import frog.calculator.register.IRegister;
import frog.calculator.register.TreeRegister;
import frog.calculator.resolver.IResolver;
import frog.calculator.resolver.IResolverBuilder;
import frog.calculator.resolver.IResolverResult;
import frog.calculator.resolver.resolve.NumberResolver;
import frog.calculator.resolver.resolve.SymbolResolver;

public class DefaultResolverBuilder implements IResolverBuilder {

    public DefaultResolverBuilder(IBuilderPrototypeHolder prototypeHolder) {
        this.register = initRegister(prototypeHolder.getPrototypeExpressions());
        this.resolver = initResolver(prototypeHolder.getNumberExpressionFactory(), prototypeHolder.getResolverResultPrototype());
    }

    private IResolver resolver;

    private IRegister register;

    private IResolver initResolver(INumberExpressionFactory numberExpressionFactory, IResolverResult resolverResultPrototype){
        SymbolResolver symbolResolver = new SymbolResolver(resolverResultPrototype);

        NumberResolver numberResolver = new NumberResolver(numberExpressionFactory, resolverResultPrototype);
        numberResolver.setNextResolver(symbolResolver);

        return numberResolver;
    }

    private IRegister initRegister(IExpression[] prototypeExps){
        TreeRegister register = new TreeRegister();
        for(IExpression exp : prototypeExps){
            register.registe(exp.symbol(), exp, exp.getOperator());
        }
        return register;
    }

    @Override
    public IResolver getResolver() {
        return this.resolver;
    }

    @Override
    public IRegister getRegister() {
        return this.register;
    }

}
