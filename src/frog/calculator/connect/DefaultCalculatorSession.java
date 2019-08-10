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
        IExpression expression = register.find(varName);
        if(expression != null){ expression = expression.clone(); }
        return expression;
    }

    @Override
    public IExpression getSessionVariable(char[] expChars, int startIndex) {
        IExpression expression = register.retrieve(expChars, startIndex);
        if(expression != null){ expression = expression.clone();}
        return expression;
    }
}
