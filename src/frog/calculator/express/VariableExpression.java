package frog.calculator.express;

import frog.calculator.exec.space.ISpace;
import frog.calculator.math.BaseNumber;

/**
 * 值变量表达式
 */
public class VariableExpression extends EndPointExpression {

    private PrototypeVariableExpression prototype;

    private IExpression value;

    String assign;

//    private final String[] variablePipe = new String[];

    private VariableExpression(String symbol, String assign) {
        super(symbol, null);
        this.assign = assign;
    }

    public static VariableExpression createVariableExpression(String symbol, String assign){
        return new PrototypeVariableExpression(symbol, assign);
    }

    @Override
    public boolean createBranch(IExpression childExpression) {
        if(childExpression.isLeaf()){
            if(this.value == null && this.assign.equals(childExpression.symbol())){
                this.value = childExpression;
                return true;
            }else if(this.prototype.protoValue == null && this.prototype.argumentList == null){ // 说明该变量未初始化
                this.prototype.argumentList = childExpression;
                return true;
            }
        }
        return false;
    }

    @Override
    public IExpression assembleTree(IExpression expression) {
        if(!this.createBranch(expression)){ // 当变量本身已经初始化完毕, 就变成一个数, 这时createBranch失败
            if(expression.createBranch(this)){
                return expression;
            }
            return null;
        }else{
            return this;
        }
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

        // 参数列表
        private IExpression argumentList;

        @Override
        public IExpression clone() {
            VariableExpression variableExpression = new VariableExpression(this.symbol, this.assign);
            variableExpression.value = null;
            variableExpression.prototype = this;
            return variableExpression;
        }
    }
}
