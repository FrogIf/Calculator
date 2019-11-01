package frog.calculator.express;

public interface IExpressionHolder {

    /**
     * 正号表达式<br/>
     * 该表达式可以作为正号, 也可以作为加法表达式
     * @return 正号表达式
     */
    IExpression getPlus();

    /**
     * 负号表达式<br/>
     * 该表达式可以作为负号, 也可以作为减法表达式
     * @return 负号表达式
     */
    IExpression getMinus();

    /**
     * 获取内置表达式<br/>
     * 所有系统内置的表达式均在这里
     * @return 内置表达式
     */
    IExpression[] getBuiltInExpression();

    /**
     * 获取结构表达式</br>
     * 这个表达式是builtInExpression的一个子集, 不参与运算, 用于分割, 组织算式结构等
     * @return 结构表达式
     */
    IExpression[] getStructureExpression();

}
