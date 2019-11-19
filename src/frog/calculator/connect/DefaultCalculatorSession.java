package frog.calculator.connect;

import frog.calculator.ICalculatorManager;
import frog.calculator.build.IExpressionBuilder;
import frog.calculator.build.register.SymbolRegister;
import frog.calculator.build.resolve.IResolverResult;
import frog.calculator.exception.DuplicateSymbolException;
import frog.calculator.express.IExpression;

public class DefaultCalculatorSession extends AbstractCalculatorSession {

    private ICalculatorManager manager;

    private IExpressionBuilder builder;

    private SymbolRegister<IExpression> sessionRegister = new SymbolRegister<>();

    public DefaultCalculatorSession(ICalculatorManager manager) {
        this.manager = manager;
        this.builder = this.manager.createExpressionBuilder(this);
    }

    @Override
    public void addVariable(IExpression expression) throws DuplicateSymbolException {
        sessionRegister.insert(expression);
    }

    @Override
    public IResolverResult resolveVariable(char[] expChars, int startIndex) {
        IExpression expression = sessionRegister.retrieve(expChars, startIndex);
        if(expression != null){
            return this.manager.createResolverResult(expression.clone());
        }
        return null;
    }

    @Override
    public IExpressionBuilder getExpressionBuilder() {
        return this.builder;
    }

    @Override
    public boolean removeVariable(String symbol) {
        return this.sessionRegister.remove(symbol);
    }

}
