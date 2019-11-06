package frog.calculator.express;

import frog.calculator.math.BaseNumber;
import frog.calculator.space.ISpace;

/**
 * 值变量表达式
 */
public class VariableExpression extends EndPointExpression {

    private PrototypeVariableExpression prototype;

    private IExpression value;

    String assign;

    private VariableExpression(String symbol, String assign) {
        super(symbol, null);
        this.assign = assign;
    }

    public static VariableExpression createVariableExpression(String symbol, String assign){
        return new PrototypeVariableExpression(symbol, assign);
    }

    @Override
    public boolean createBranch(IExpression childExpression) {
        if(this.value == null && this.assign.equals(childExpression.symbol()) && childExpression.isLeaf()){
            this.value = childExpression;
            return true;
        }
        return false;
    }

    @Override
    public IExpression assembleTree(IExpression expression) {
        if(this.value == null && this.assign.equals(expression.symbol()) && expression.isLeaf()){
            this.value = expression;
            return this;
        }
        if(expression.createBranch(this)){
            return expression;
        }
        return null;
    }

    @Override
    public ISpace<BaseNumber> interpret() {
        ISpace<BaseNumber> result;
        if(this.value != null){
            result = this.value.interpret();
        }else if(prototype.protoValue != null){
            result = prototype.protoValue;
        }else {
            throw new IllegalStateException("variable " + this.symbol + " is not assign.");
        }
        this.prototype.protoValue = result;
        return result;
    }

    @Override
    public IExpression clone() {
        return this;
    }

    private static class PrototypeVariableExpression extends VariableExpression {

        private PrototypeVariableExpression(String symbol, String assign) {
            super(symbol, assign);
        }

        private ISpace<BaseNumber> protoValue;

        @Override
        public IExpression clone() {
            VariableExpression variableExpression = new VariableExpression(this.symbol, this.assign);
            variableExpression.value = null;
            variableExpression.prototype = this;
            return variableExpression;
        }
    }
}
