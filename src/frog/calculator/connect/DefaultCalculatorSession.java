package frog.calculator.connect;

import frog.calculator.register.IRegister;
import frog.calculator.register.TreeRegister;
import frog.calculator.resolver.IResolver;

public class DefaultCalculatorSession implements ICalculatorSession {

    private IResolver currentResolver;  // 记录当前用户使用的解析器

    private TreeRegister register = new TreeRegister();

    @Override
    public void setCurrentResolver(IResolver resolver) {
        this.currentResolver = resolver;
    }

    @Override
    public IResolver getCurrentResolver() {
        return this.currentResolver;
    }

    @Override
    public IRegister getUserRegister() {
        return this.register;
    }


}
