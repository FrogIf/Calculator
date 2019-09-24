package frog.calculator;

import frog.calculator.express.IExpression;
import frog.calculator.express.separator.SeparatorExpression;
import frog.calculator.operator.IOperator;
import frog.calculator.operator.base.AddOperator;
import frog.calculator.operator.base.NumberOperator;

public class OriginExpressionHolder extends AbstractExpressionHolder{

    // 正
    private IExpression plus = new SeparatorExpression("+", 1, new AddOperator());

    // 负
    private IExpression minus = new SeparatorExpression("+", 1, new AddOperator());

    private IOperator numberOperator = new NumberOperator();

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

    @Override
    public IOperator getNumberOperator() {
        return this.numberOperator;
    }
}
