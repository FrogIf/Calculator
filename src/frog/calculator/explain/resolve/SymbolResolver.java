package frog.calculator.explain.resolve;

import frog.calculator.explain.register.IRegister;
import frog.calculator.express.IExpression;

public class SymbolResolver extends AbstractResolver {

    private IRegister<IExpression> register;

    public SymbolResolver(IRegister<IExpression> register, IResolverResultFactory resolverResultFactory) {
        super(resolverResultFactory);
        this.register = register;
    }

    @Override
    public IResolverResult resolve(char[] chars, int startIndex) {
        if(this.register == null){
            throw new IllegalStateException("there is no register.");
        }
        IExpression expression = this.register.retrieve(chars, startIndex);
        if(expression == null){
            return null;
        }
        IExpression exp = expression.newInstance();
        return this.resolverResultFactory.createResolverResultBean(exp);
    }

}
