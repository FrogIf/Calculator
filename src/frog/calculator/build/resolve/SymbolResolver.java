package frog.calculator.build.resolve;

import frog.calculator.ICalculatorManager;
import frog.calculator.express.IExpression;
import frog.calculator.build.register.IRegister;

public class SymbolResolver extends AbstractResolver {

    private IRegister<IExpression> register;

    public SymbolResolver(ICalculatorManager manager, IRegister<IExpression> register) {
        super(manager);
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
        IExpression exp = expression.clone();
        return this.manager.createResolverResult(exp);
    }

}
