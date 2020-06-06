package frog.calculator.execute.holder;

import frog.calculator.execute.base.*;
import frog.calculator.execute.ext.FactorialOpr;
import frog.calculator.execute.ext.PercentOpr;
import frog.calculator.execute.fun.AverageOpr;
import frog.calculator.execute.fun.SqrtOpr;
import frog.calculator.execute.fun.SumOpr;
import frog.calculator.express.*;
import frog.calculator.express.LeftExpression;
import frog.calculator.express.MiddleExpression;
import frog.calculator.express.RightExpression;

/**
 * 系统原生表达式持有者, 是系统默认的表达式集, explain依赖该符号与表达式之间的映射关系进行explain
 */
public class OriginExpressionHolder extends AbstractExpressionHolder {

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
