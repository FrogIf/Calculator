package frog.calculator.connect;

import frog.calculator.express.IExpression;
import frog.calculator.register.TreeRegister;

public class DefaultCalculatorSession implements ICalculatorSession {

    private TreeRegister register = new TreeRegister();

    public DefaultCalculatorSession() { }

    @Override
    public void addSessionVariable(IExpression expression) {
        register.insert(expression);
    }

    @Override
    public IExpression getSessionVariable(String varName) {
        return register.find(varName);
    }

    @Override
    public IExpression getSessionVariable(char[] expChars, int startIndex) {
        return register.retrieve(expChars, startIndex);
    }
}
