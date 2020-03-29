package frog.calculator.explain;

import frog.calculator.express.IExpression;

public interface IBuildFinishListener {

    IExpression buildFinishCallBack(IExpressionBuilder context);

}
