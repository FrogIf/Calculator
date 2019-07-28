package frog.calculator.connect;

import frog.calculator.register.IRegister;
import frog.calculator.resolver.IResolver;

public interface ICalculatorSession {
    IResolver getCurrentResolver();

    /**
     * 用户变量存储位置
     * @return
     */
    IRegister getUserRegister();

    void setCurrentResolver(IResolver resolver);

}
