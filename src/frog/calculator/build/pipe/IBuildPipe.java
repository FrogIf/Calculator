package frog.calculator.build.pipe;

import frog.calculator.build.IExpressionBuilder;
import frog.calculator.exception.DuplicateSymbolException;

public interface IBuildPipe {

    String symbol();

    void matchCallBack(IExpressionBuilder builder) throws DuplicateSymbolException;

}
