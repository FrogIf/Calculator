package frog.calculator.build.pipe;

import frog.calculator.build.IExpressionBuilder;

/**
 * 管道终点回调
 */
public interface IPipeEndCallback {

    void run(IExpressionBuilder builder);

}
