package frog.calculator;

import frog.calculator.connect.ICalculatorSession;

public interface ICalculator<R> {

    public R calculate(String expression, ICalculatorSession session);
    
}
