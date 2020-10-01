package frog.calculator.connect;

import frog.calculator.ICalculatorManager;
import frog.calculator.build.register.SymbolRegister;
import frog.calculator.build.resolve.IResolverResult;
import frog.calculator.build.resolve.IResolverResultFactory;
import frog.calculator.exception.DuplicateSymbolException;
import frog.calculator.express.IExpression;

public class DefaultCalculatorSession extends AbstractCalculatorSession {

    private final SymbolRegister<IExpression> sessionRegister = new SymbolRegister<>();

    private final IResolverResultFactory resolverResultFactory;

    public DefaultCalculatorSession(ICalculatorManager calculatorManager) {
        this.resolverResultFactory = calculatorManager.getExplainManager().getResolverFactory();
    }

    @Override
    public void addVariable(IExpression expression) throws DuplicateSymbolException {
        sessionRegister.insert(expression);
    }

    @Override
    public IResolverResult resolve(char[] expChars, int startIndex) {
        IExpression expression = sessionRegister.retrieve(expChars, startIndex);
        return this.resolverResultFactory.createResolverResultBean(expression);
    }

    @Override
    public boolean removeVariable(String symbol) {
        return this.sessionRegister.remove(symbol);
    }

}
