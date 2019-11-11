package frog.calculator.build;

import frog.calculator.express.IExpression;

public interface IBuildFinishListener {

    IExpression buildFinishCallBack(IExpressionBuilder context);

}
