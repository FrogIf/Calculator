package frog.calculator.express;

import frog.calculator.connect.ICalculatorSession;
import frog.calculator.register.SymbolRegister;

public class DefaultExpressionContext implements IExpressionContext {

    /**
     * 用户变量存储在这里
     */
    private SymbolRegister register = new SymbolRegister();

    private ICalculatorSession session;

    public DefaultExpressionContext(ICalculatorSession session) {
        this.session = session;
    }

    @Override
    public IExpression getLocalVariable(String varName) {
        return register.find(varName);
    }

    @Override
    public void addLocalVariable(IExpression expression) {
        register.insert(expression);
    }

    @Override
    public ICalculatorSession getSession() {
        return this.session;
    }

    @Override
    public IExpressionContext newInstance() {
        // TODO 未完成, 应该使用栈结构(Last in First out), 考虑多层嵌套变量表
        DefaultExpressionContext context = new DefaultExpressionContext(this.session);
        return context;
    }
}
