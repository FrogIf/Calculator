package frog.calculator.express;

public interface IExpressionContext {

    int getCurrentMaxBuildFactor();

    int getCurrentMinBuildFactor();

    /**
     * 该表达式树的变量池
     * @return
     */
    IExpression[] getVariables();

    void addVariables(IExpression expression);

    void monitor(IExpression expression);

    IExpressionContext newInstance();
}
