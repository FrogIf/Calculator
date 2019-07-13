package frog.calculator.express;

public interface IExpressionContext {

    int getCurrentMaxBuildFactor();

    int getCurrentMinBuildFactor();

    void monitor(IExpression expression);

}
