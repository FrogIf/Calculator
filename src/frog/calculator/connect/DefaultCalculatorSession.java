package frog.calculator.connect;

import frog.calculator.build.register.SymbolRegister;
import frog.calculator.build.resolve.CommonResolveResultFactory;
import frog.calculator.build.resolve.IResolveResult;
import frog.calculator.build.resolve.IResolveResultFactory;
import frog.calculator.exception.DuplicateSymbolException;
import frog.calculator.express.IExpression;

public class DefaultCalculatorSession extends AbstractCalculatorSession {

    private final SymbolRegister<IExpression> sessionRegister = new SymbolRegister<>();

    private final IResolveResultFactory resolverResultFactory = new CommonResolveResultFactory();

    @Override
    public void addVariable(IExpression expression) throws DuplicateSymbolException {
        sessionRegister.insert(expression);
    }

    @Override
    public IResolveResult resolve(char[] expChars, int startIndex) {
        IExpression expression = sessionRegister.retrieve(expChars, startIndex);
        return this.resolverResultFactory.createResolverResultBean(expression == null ? null : expression.newInstance());
    }

    @Override
    public boolean removeVariable(String symbol) {
        return this.sessionRegister.remove(symbol);
    }

}
