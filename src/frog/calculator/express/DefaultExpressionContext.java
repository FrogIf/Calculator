package frog.calculator.express;

import frog.calculator.connect.ICalculatorSession;
import frog.calculator.util.LinkedList;

public class DefaultExpressionContext implements IExpressionContext {

    private LinkedList<IExpression> varibleList = new LinkedList<>();

    private ICalculatorSession session;

    public DefaultExpressionContext(ICalculatorSession session) {
        this.session = session;
    }

    @Override
    public LinkedList<IExpression> getVariables() {
        return this.varibleList;
    }

    @Override
    public ICalculatorSession getSession() {
        return this.session;
    }

    @Override
    public void addLocalVariables(IExpression expression) {
        varibleList.add(expression);
    }

    @Override
    public IExpressionContext newInstance() {
        return new DefaultExpressionContext(this.session);
    }
}
