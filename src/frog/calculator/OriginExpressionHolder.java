package frog.calculator;

import frog.calculator.express.IExpression;
import frog.calculator.express.LeftExpression;
import frog.calculator.express.MiddleExpression;
import frog.calculator.express.RightExpression;
import frog.calculator.operator.base.*;
import frog.calculator.operator.ext.FactorialOpr;
import frog.calculator.operator.ext.PercentOpr;
import frog.calculator.operator.fun.AverageOpr;
import frog.calculator.operator.fun.SqrtOpr;
import frog.calculator.operator.fun.SumOpr;

public class OriginExpressionHolder extends AbstractExpressionHolder{

    // 正
    private IExpression plus = new MiddleExpression("+", 1, new AddOpr());

    // 负
    private IExpression minus = new MiddleExpression("-", 1, new SubOpr());

    @Override
    protected IExpression[] getRunnableExpression() {
        return new IExpression[]{
                new MiddleExpression("*", 2, new MultOpr()),
                new MiddleExpression("/", 2, new DivOpr()),
                new MiddleExpression("^", 2, new PowerOpr()),
                new RightExpression("%", 4, new PercentOpr()),
                new RightExpression("!", 4, new FactorialOpr()),
                new RightExpression("i", 4, new ComplexMarkOpr()),
                new LeftExpression("sqrt", 4, new SqrtOpr()),
                new LeftExpression("avg", 4, new AverageOpr()),
                new LeftExpression("sum", 4, new SumOpr())
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
}
