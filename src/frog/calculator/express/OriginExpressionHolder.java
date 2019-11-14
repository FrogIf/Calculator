package frog.calculator.express;

import frog.calculator.exec.base.*;
import frog.calculator.exec.ext.FactorialOpr;
import frog.calculator.exec.ext.PercentOpr;
import frog.calculator.exec.fun.AverageOpr;
import frog.calculator.exec.fun.SqrtOpr;
import frog.calculator.exec.fun.SumOpr;

public class OriginExpressionHolder extends AbstractExpressionHolder{

    // 正
    private IExpression plus = new MiddleExpression("+", 1, new AddOpr());

    // 负
    private IExpression minus = new MiddleExpression("-", 1, new SubOpr());

    private static final int FUNCTION_BUILD_FACTOR = 5;

    @Override
    protected IExpression[] getRunnableExpression() {
        return new IExpression[]{
                new MiddleExpression("*", 2, new MultOpr()),
                new MiddleExpression("/", 2, new DivOpr()),
                new MiddleExpression("^", 2, new PowerOpr()),
                new RightExpression("%", 4, new PercentOpr()),
                new RightExpression("!", 4, new FactorialOpr()),
                new RightExpression("i", 4, new ComplexMarkOpr()),
                new LeftExpression("sqrt", FUNCTION_BUILD_FACTOR, new SqrtOpr()),
                new LeftExpression("avg", FUNCTION_BUILD_FACTOR, new AverageOpr()),
                new LeftExpression("sum", FUNCTION_BUILD_FACTOR, new SumOpr())
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
