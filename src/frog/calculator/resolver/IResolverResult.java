package frog.calculator.resolver;

import frog.calculator.express.IExpression;

public interface IResolverResult extends Cloneable {
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

    void setEndIndex(int index);

    void setExpression(IExpression expression);

    void setSymbol(String symbol);

    IResolverResult clone();
}
