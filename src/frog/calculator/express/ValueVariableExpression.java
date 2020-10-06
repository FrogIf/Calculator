package frog.calculator.express;

import frog.calculator.execute.space.ISpace;
import frog.calculator.math.number.BaseNumber;

public class ValueVariableExpression extends EndPointExpression {

    private ISpace<BaseNumber> value;

    public ValueVariableExpression(String symbol) {
        super(symbol);
    }

    public void assign(ISpace<BaseNumber> baseNumber){
        this.value = baseNumber;
    }

    @Override
    public IExpression newInstance() {
        return this;
    }

    @Override
    public ISpace<BaseNumber> interpret() {
        if(value == null){
            throw new IllegalStateException("the variable has not assign.");
        }
        return value;
    }

}
