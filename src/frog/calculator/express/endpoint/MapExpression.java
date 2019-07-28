package frog.calculator.express.endpoint;

import frog.calculator.express.IExpression;

public class MapExpression extends EndPointExpression {
    private IExpression value;
    public MapExpression(String key, IExpression value) {
        super(key, null);
        this.value = value;
    }

    @Override
    public IExpression interpret() {
        return this.value.interpret();
    }
}
