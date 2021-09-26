package sch.frog.calculator;

import sch.frog.calculator.runtime.ICalculatorSession;

public interface ICalculator<R> {

    public R calculate(String expression, ICalculatorSession session);

    // formula  把一个字符串解析成公式
    
}
