package frog.calculator;

import frog.calculator.express.IExpression;
import frog.calculator.express.LeftExpression;
import frog.calculator.express.MiddleExpression;
import frog.calculator.express.RightExpression;
import frog.calculator.operator.IOperator;
import frog.calculator.operator.base.*;
import frog.calculator.operator.ext.FactorialOpr;
import frog.calculator.operator.ext.PercentOperator;
import frog.calculator.operator.fun.AverageOperator;
import frog.calculator.operator.fun.SqrtOperator;

public class OriginExpressionHolder extends AbstractExpressionHolder{

    // 正
    private IExpression plus = new MiddleExpression("+", 1, new AddOperator());

    // 负
    private IExpression minus = new MiddleExpression("-", 1, new SubOperator());

    // 数字表达式运算器
    private IOperator numberOperator = new NumberOperator();

    @Override
    protected IExpression[] getRunnableExpression() {
        return new IExpression[]{
                new MiddleExpression("*", 2, new MultOperator()),
                new MiddleExpression("/", 2, new DivOperator()),
                new MiddleExpression("^", 2, new PowerOperator()),
                new RightExpression("%", 4, new PercentOperator()),
                new RightExpression("!", 4, new FactorialOpr()),
                new RightExpression("i", 4, new ComplexMarkOperator()),
                new LeftExpression("sqrt", 4, new SqrtOperator()),
                new LeftExpression("avg", 4, new AverageOperator())
        };
    }

    @Override
    public IExpression getPlus() {
        return plus;
    }

    @Override
    public IExpression getMinus() {
        return minus;
    }

    @Override
    public IOperator getNumberOperator() {
        return this.numberOperator;
    }
}
