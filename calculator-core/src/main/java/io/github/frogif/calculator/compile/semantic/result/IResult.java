package io.github.frogif.calculator.compile.semantic.result;

import io.github.frogif.calculator.compile.semantic.NoValueException;

public interface IResult {

    enum ResultType {
        VOID,
        VALUE,
        UNKNOWN
    }

    ResultType getResultType();

    /**
     * 获取value
     * @return value
     * @throws NoValueException 没有可以获取到的值
     */
    IValue getValue() throws NoValueException;
    
}
