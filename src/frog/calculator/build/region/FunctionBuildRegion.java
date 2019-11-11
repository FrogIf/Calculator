package frog.calculator.build.region;

import frog.calculator.express.IExpression;

public class FunctionBuildRegion implements IBuildRegion {

    private String[] symbol;

    private int age;

    public FunctionBuildRegion(String[] symbol) {
        if(symbol == null){
            throw new IllegalArgumentException("age and symbol array is necessary.");
        }
        this.symbol = symbol;
        this.age = 2;
    }

    @Override
    public boolean match(IExpression expression) {
        if(symbol[age].equals(expression.symbol())){
            this.age++;
            return true;
        }
        return false;
    }
}
