package frog.calculator.resolver.resolve;

import frog.calculator.express.IExpression;
import frog.calculator.register.IRegister;
import frog.calculator.resolver.IResolverResult;
import frog.calculator.resolver.IResolverResultFactory;

public class SymbolResolver extends AbstractResolver {

    private IRegister register;

    public SymbolResolver(IResolverResultFactory resolverResultFactory, IRegister innerSymbolRegister) {
        super(resolverResultFactory);
        this.register = innerSymbolRegister;
    }

    @Override
    protected void resolve(char[] chars, int startIndex, IResolverResult resolveResult) {
        if(this.register == null){
            throw new IllegalStateException("there is no register.");
        }
        IRegister registry = this.register.retrieveRegistryInfo(chars, startIndex);
        if(registry == null){
            return;
        }else{
            IExpression expression = registry.getExpression();
            if(expression == null){
                return;
            }
            IExpression exp = expression.clone();
            resolveResult.setExpression(exp);
            String completeSymbol = exp.symbol();
            if(completeSymbol != null){
                resolveResult.setSymbol(completeSymbol);
                resolveResult.setEndIndex(startIndex + completeSymbol.length() - 1);
            }
        }
    }

}
