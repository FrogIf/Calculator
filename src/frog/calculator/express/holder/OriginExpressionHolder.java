package frog.calculator.express.holder;

import frog.calculator.execute.base.*;
import frog.calculator.execute.ext.FactorialOpr;
import frog.calculator.execute.ext.PercentOpr;
import frog.calculator.execute.fun.AverageOpr;
import frog.calculator.execute.fun.SqrtOpr;
import frog.calculator.execute.fun.SumOpr;
import frog.calculator.express.*;
import frog.calculator.util.Arrays;

/**
 * 系统原生表达式持有者, 是系统默认的表达式集, explain依赖该符号与表达式之间的映射关系进行explain
 */
public class OriginExpressionHolder implements IExpressionHolder {

    public static final String PLUS = "+";

    public static final String MINUS = "-";

    public static final String ASSIGN = "=";

    /**
     * 函数的构建因子, 也就是函数的优先级
     */
    private static final int FUNCTION_BUILD_FACTOR = 5;

    private static final IExpression[] expressions = new IExpression[]{
            new MiddleExpression(ASSIGN, 0, new AssignOpr()),  // 等于
            new MiddleExpression(PLUS, 1, new AddOpr()),  // 加
            new MiddleExpression(MINUS, 1, new SubOpr()), // 减
            new MiddleExpression("*", 2, new MultOpr()),    // 乘
            new MiddleExpression("/", 2, new DivOpr()), // 除
            new SignalExpression(","),  // TODO 改为middle expression, 优先级为0, 分割
            new ContainerExpression("(", ",", ")", new BracketOpr()),   // 左括号(
            new SignalExpression(")"),  // 右括号(
            new ContainerExpression("[", ",", "]", new BracketOpr()),   // 左方括号[
            new SignalExpression("]"),  // 右方括号]
            new ContainerExpression("{", ",", "}", new RegionOpr()),    // 左大括号{
            new SignalExpression("}"),  // 右大括号]
            new MiddleExpression("^", 2, new PowerOpr()),   // 幂运算
            new RightExpression("%", 4, new PercentOpr()),  // 百分号
            new RightExpression("!", 4, new FactorialOpr()),    // 阶乘
            new RightExpression("i", 4, new ComplexMarkOpr()),  // 虚数符号
            new LeftExpression("sqrt", FUNCTION_BUILD_FACTOR, new SqrtOpr()),   // 开平方
            new LeftExpression("avg", FUNCTION_BUILD_FACTOR, new AverageOpr()), // 求平均数
            new LeftExpression("sum", FUNCTION_BUILD_FACTOR, new SumOpr())  // 求和
    };

    /**
     * 表达式定义检查
     */
    private static void check(){
        // TODO 检查
    }


    @Override
    public IExpression[] getExpressions() {
        IExpression[] target = new IExpression[expressions.length];
        Arrays.copy(expressions, target, 0, expressions.length - 1);
        return target;
    }

    @Override
    public IExpression getExpressionBySymbol(String symbol) {
        for (IExpression expression : expressions) {
            if(symbol.equals(expression.symbol())){
                return expression;
            }
        }
        return null;
    }
}
