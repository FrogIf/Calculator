package frog.calculator.resolve.resolver;

import frog.calculator.express.IExpression;
import frog.calculator.register.IRegister;
import frog.calculator.resolve.IResolveResult;

public class SymbolResolver extends AResolver {

    private IRegister register = null;

    public SymbolResolver(IResolveResultFactory resolveResultFactory) {
        super(resolveResultFactory);
    }

    @Override
    public void setRegister(IRegister register) {
        this.register = register;
        if(this.getNext() != null){
            this.getNext().setRegister(register);
        }
    }

    @Override
    protected void resolve(char[] chars, int startIndex, IResolveResult resolveResult) {
        if(this.register == null){
            throw new IllegalStateException("there is no register.");
        }
        IRegister registry = this.register.retrieveRegistryInfo(chars, startIndex);
        if(registry == null){
            throw new IllegalArgumentException("unrecognize expression.");
        }else{
            IExpression expression = registry.getExpression();
            if(expression == null){
                throw new IllegalStateException("can't get express. char start : " + chars[startIndex]);
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
