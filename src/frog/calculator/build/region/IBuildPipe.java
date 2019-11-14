package frog.calculator.build.region;

import frog.calculator.build.IExpressionBuilder;

public interface IBuildPipe {

    String symbol();

    void matchCallBack(IExpressionBuilder builder);

}
