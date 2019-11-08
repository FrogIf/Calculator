package frog.calculator.connect;

import frog.calculator.express.IExpression;
import frog.calculator.register.IRegister;

public abstract class AbstractCalculatorSession implements ICalculatorSession{

    // 瞬时变量域
    private IRegister<IExpression> transientVariableRegion;

    @Override
    public void setTransientVariableRegion(IRegister<IExpression> expression) {
        transientVariableRegion = expression;
    }

    @Override
    public IRegister<IExpression> fetchTransientVariableRegion() {
        IRegister<IExpression> res = this.transientVariableRegion;
        this.transientVariableRegion = null;
        return res;
    }

}
