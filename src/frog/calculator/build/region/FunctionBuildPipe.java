package frog.calculator.build.region;

import frog.calculator.build.IExpressionBuilder;
import frog.calculator.build.register.IRegister;
import frog.calculator.express.IExpression;

public class FunctionBuildPipe implements IBuildPipe {

    private String[] symbol;

    private int age;

    private IRegister<IExpression> register;

    public FunctionBuildPipe(String[] symbol) {
        if(symbol == null || symbol.length == 0){
            throw new IllegalArgumentException("age and symbol array is necessary.");
        }
        this.symbol = symbol;
    }

    public void setRegister(IRegister<IExpression> register) {
        this.register = register;
    }

    @Override
    public String symbol() {
        if(this.age == this.symbol.length){ return null; }
        return this.symbol[this.age++];
    }

    @Override
    public void matchCallBack(IExpressionBuilder builder) {
        if(this.age == symbol.length && this.register != null){

        }
    }
}
