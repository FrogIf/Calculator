package frog.calculator.build.pipe;

import frog.calculator.build.IExpressionBuilder;

public interface IBuildPipe {

    String symbol();

    void matchCallBack(IExpressionBuilder builder);

}
