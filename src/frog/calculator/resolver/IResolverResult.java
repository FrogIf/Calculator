package frog.calculator.resolver;

import frog.calculator.express.IExpression;

public interface IResolverResult {
    /**
     * 记录解析器解析结果表达式
     * @return 解析后的表达式
     */
    IExpression getExpression();

    /**
     * 记录解析器解析结果最后一位的索引
     * 注意endIndex所在位置也是表达式字符的一部分
     * @return 解析终止位置
     */
    int offset();

    void setOffset(int index);
}
