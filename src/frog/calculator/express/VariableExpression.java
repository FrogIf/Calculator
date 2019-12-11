package frog.calculator.express;

import frog.calculator.exec.space.ISpace;
import frog.calculator.math.BaseNumber;

public class VariableExpression extends EndPointExpression {

    private String assign;

    private ISpace<BaseNumber> value;

    VariableExpression prototype;

    private VariableExpression realVariable;

    private final static short TYPE_VOID_VAR = 0;

    private final static short TYPE_VALUE_VAR = 1;

    private final static short TYPE_FUN_VAR = 2;

    private short type; // 变量类型: 0 - void变量, 1 - 值变量, 2 - 函数变量

    private VariableExpression(String symbol, VariableExpression prototype){
        super(symbol, null);
        this.prototype = prototype;
    }

    public VariableExpression(String symbol, String assign) {
        super(symbol, null);
        this.assign = assign;
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
    public VariableExpression clone() {
        VariableExpression variableExpression = null;
        if(this.type == TYPE_VOID_VAR){
            variableExpression = new VoidVariableExpression(this.symbol, this);
        }else if(this.type == TYPE_VALUE_VAR){
            variableExpression = new ValueVariableExpression(this.symbol, this);
        }else if(this.type == TYPE_FUN_VAR){
            variableExpression = new FunctionVariableExpression(this.symbol, this);
        }
        if(variableExpression != null) {
            variableExpression.value = null;
        }

        return variableExpression;
    }

    @Override
    public ISpace<BaseNumber> interpret() {
        return this.prototype.realVariable.interpret();
    }

    /**
     * 函数变量表达式
     */
    private static class FunctionVariableExpression extends VariableExpression{
        public FunctionVariableExpression(String symbol, VariableExpression prototype) {
            super(symbol, prototype);
        }

        private IExpression body;

        private IExpression args;

        @Override
        public boolean createBranch(IExpression childExpression) {  // TODO 函数表达式
            if(!childExpression.isLeaf()){
                return false;
            }
            if(this.args == null){
                this.args = childExpression;
            }else if(this.body == null && this.prototype.assign.equals(childExpression.symbol())){
                this.body = childExpression;
            }else{
                return false;
            }
            return true;
        }
    }

    /**
     * 值变量表达式
     */
    private static class ValueVariableExpression extends VariableExpression{

        public ValueVariableExpression(String symbol, VariableExpression prototype) {
            super(symbol, prototype);
        }

        private IExpression value;

        @Override
        public boolean createBranch(IExpression childExpression) {
            if(childExpression.isLeaf() && this.prototype.assign.equals(childExpression.symbol()) && this.value == null){   // 只有叶子节点才可能作为变量表达式的子表达式
                this.value = childExpression;
                return true;
            }
            return false;
        }

        @Override
        public  ISpace<BaseNumber> interpret() {
            ISpace<BaseNumber> result;
            if(this.value != null){ // 重新赋值
                result = this.value.interpret();
            }else if(this.prototype.value != null){ // 没有重新赋值, 存在默认值
                result = this.prototype.value;
            }else {
                throw new IllegalStateException("variable " + this.symbol + " is not assign.");
            }
            this.prototype.value = result;
            return result;
        }

        @Override
        public VariableExpression clone() {
            return this;
        }
    }

    /**
     * 泛型变量, 类型还未确定的变量
     */
    private static class VoidVariableExpression extends VariableExpression{
        public VoidVariableExpression(String symbol, VariableExpression prototype) {
            super(symbol, prototype);
        }

        @Override
        public boolean createBranch(IExpression childExpression) {
            if(childExpression.isLeaf()){   // 只有叶子节点才可能作为变量表达式的子表达式
                if(this.prototype.realVariable == null){
                    if(this.prototype.assign.equals(childExpression.symbol())){
                        this.prototype.type = VariableExpression.TYPE_VALUE_VAR;
                    }else{
                        this.prototype.type = VariableExpression.TYPE_FUN_VAR;
                    }
                    VariableExpression varExp = this.prototype.clone();
                    if(varExp.createBranch(childExpression)){
                        this.prototype.realVariable = varExp;
                        return true;
                    }else{
                        return false;
                    }
                }else{
                    return this.prototype.realVariable.createBranch(childExpression);
                }
            }
            return false;
        }

        @Override
        public VariableExpression clone() {
            return this;
        }
    }
}
