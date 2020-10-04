package frog.calculator.build.resolve;

import frog.calculator.build.register.IRegister;
import frog.calculator.express.IExpression;

public class SymbolResolver extends AbstractResolver {

    private final IRegister<IExpression> register;

    private final IResolveResultFactory resolveResultFactory = new CommonResolveResultFactory();

    public SymbolResolver(IRegister<IExpression> register) {
        this.register = register;
    }

    @Override
    public IResolveResult resolve(char[] chars, int startIndex) {
        if(this.register == null){
            throw new IllegalStateException("there is no register.");
        }
        IExpression expression = this.register.retrieve(chars, startIndex);
        if(expression == null){
            return EMPTY_RESULT;
        }
        IExpression exp = expression.newInstance();
        return this.resolveResultFactory.createResolverResultBean(exp);
    }

}
