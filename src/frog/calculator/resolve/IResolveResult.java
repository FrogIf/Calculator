package frog.calculator.resolve;

import frog.calculator.express.IExpression;

public interface IResolveResult {
    /**
     * 记录解析器解析结果表达式
     * @return
     */
    IExpression getExpression();

    /**
     * 记录解析器解析结果最后一位的索引
     * 注意endIndex所在位置也是表达式字符的一部分
     * @return
     */
    int getEndIndex();

    /**
     * 获取运算符
     * @return
     */
    String getSymbol();
}
