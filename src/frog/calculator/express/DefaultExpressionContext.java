package frog.calculator.express;

import frog.calculator.connect.ICalculatorSession;
import frog.calculator.util.LinkedList;

public class DefaultExpressionContext implements IExpressionContext {

    private LinkedList<IExpression> localVariableList = new LinkedList<>();

    private ICalculatorSession session;

    public DefaultExpressionContext(ICalculatorSession session) {
        this.session = session;
    }

    @Override
    public LinkedList<IExpression> getLocalVariables() {
        return this.localVariableList;
    }

    @Override
    public ICalculatorSession getSession() {
        return this.session;
    }

    @Override
    public void addLocalVariables(IExpression expression) {
        localVariableList.add(expression);
    }

    @Override
    public IExpressionContext newInstance() {
        // TODO 应该使用栈结构(Last in First out)
        DefaultExpressionContext context = new DefaultExpressionContext(this.session);
        context.localVariableList = this.localVariableList;
        return context;
    }
}
