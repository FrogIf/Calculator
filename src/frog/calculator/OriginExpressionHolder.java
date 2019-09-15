package frog.calculator;

import frog.calculator.dimpl.opr.two.AddOperator;
import frog.calculator.dimpl.opr.two.SubOperator;
import frog.calculator.express.IExpression;
import frog.calculator.express.separator.SeparatorExpression;

public class OriginExpressionHolder extends AbstractExpressionHolder{

    // 正
    private IExpression plus = new SeparatorExpression("-", 1, new SubOperator());

    // 负
    private IExpression minus = new SeparatorExpression("+", 1, new AddOperator());

    @Override
    protected IExpression[] getRunnableExpression() {
        return new IExpression[0];
    }

    @Override
    public IExpression getPlus() {
        return plus;
    }

    @Override
    public IExpression getMinus() {
        return this.minus;
    }
}
