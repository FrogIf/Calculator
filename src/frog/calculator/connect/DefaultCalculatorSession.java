package frog.calculator.connect;

import frog.calculator.ICalculatorManager;
import frog.calculator.exception.DuplicateSymbolException;
import frog.calculator.explain.DefaultExpressionBuilder;
import frog.calculator.explain.IExpressionBuilder;
import frog.calculator.explain.register.SymbolRegister;
import frog.calculator.express.IExpression;

public class DefaultCalculatorSession extends AbstractCalculatorSession {

    private SymbolRegister<IExpression> sessionRegister = new SymbolRegister<>();

    private IExpressionBuilder builder;

    public DefaultCalculatorSession(ICalculatorManager calculatorManager) {
        this.builder = new DefaultExpressionBuilder(this, calculatorManager.getExplainManager());
    }

    @Override
    public void addVariable(IExpression expression) throws DuplicateSymbolException {
        sessionRegister.insert(expression);
    }

    @Override
    public IExpression resolveVariable(char[] expChars, int startIndex) {
        return sessionRegister.retrieve(expChars, startIndex);
    }

    @Override
    public boolean removeVariable(String symbol) {
        return this.sessionRegister.remove(symbol);
    }

    @Override
    public IExpressionBuilder getBuilder() {
        return this.builder;
    }

}
