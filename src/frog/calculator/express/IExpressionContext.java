package frog.calculator.express;

/**
 * 表达式上下文
 */
public interface IExpressionContext {

    void setRoot(IExpression root);

    IExpression getRoot();

    void finishBuild();

    void addBuildFinishListener(IBuildFinishListener listener);

}
