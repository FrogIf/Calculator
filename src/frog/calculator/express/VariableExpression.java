package frog.calculator.express;

import frog.calculator.execute.space.ISpace;
import frog.calculator.math.number.BaseNumber;
import frog.calculator.util.collection.IList;
import frog.calculator.util.collection.LinkedList;

public class VariableExpression extends EndPointExpression {

    /**
     * 赋值符号
     */
    private final String assign;

    /**
     * 变量的当前值
     */
    private ISpace<BaseNumber> value;

    /**
     * 变量值
     */
    IExpression valueExpression;

    /**
     * 变量的原型变量
     */
    VariableExpression prototype;

    /**
     * 实际变量
     */
    private VariableExpression realVariable;

    private final static short TYPE_VOID_VAR = 0;

    private final static short TYPE_VALUE_VAR = 1;

    private final static short TYPE_FUN_VAR = 2;

    private final static short TYPE_FORMAT_ARG = 3;

    /**
     * 变量类型<br/>
     * 0 - void变量<br/>
     * 1 - 值变量<br/>
     * 2 - 函数变量<br/>
     * 3 - 形参变量
     */
    private short type;

    private VariableExpression(String symbol, VariableExpression prototype){
        super(symbol, null);
        this.prototype = prototype;
        this.assign = prototype.assign;
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
        switch (this.type){
            case TYPE_VOID_VAR:
                variableExpression = new VoidVariableExpression(this.symbol, this);
                break;
            case TYPE_VALUE_VAR:
                variableExpression = new ValueVariableExpression(this.symbol, this);
                break;
            case TYPE_FUN_VAR:
                variableExpression = new FunctionVariableExpression(this.symbol, this);
                break;
            case TYPE_FORMAT_ARG:
                variableExpression = new FormatArgumentExpression(this.symbol, this);
                break;
            default:
                break;
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
            if(prototype.realVariable instanceof FunctionVariableExpression){
                FunctionVariableExpression protoFun = (FunctionVariableExpression) prototype.realVariable;
                this.args = protoFun.args;
            }
        }

        /**
         * 形参表达式
         */
        private IList<VariableExpression> args;

        @Override
        public boolean createBranch(IExpression childExpression) {
            if(!childExpression.isLeaf()){
                return false;
            }
            if(this.prototype.realVariable == this){    // 函数定义阶段
                boolean isAssign = this.prototype.assign.equals(childExpression.symbol());
                if(this.args == null && !isAssign){ // 函数形参
                    this.args = new LinkedList<>();
                    while(childExpression.hasNextChild()){
                        IExpression exp = childExpression.nextChild();
                        if(exp instanceof VariableExpression){
                            VariableExpression arg = ((VariableExpression) exp);
                            arg.prototype.type = VariableExpression.TYPE_FORMAT_ARG;
                            this.args.add(arg.prototype);
                        }else{
                            this.args = null;
                            return false;
                        }
                    }
                }else if(this.valueExpression == null && isAssign){ // 函数体
                    this.valueExpression = childExpression;
                }else{
                    return false;
                }
            }else{  // 函数实参注入
                if(this.args == null){ return false; }


//                Iterator<VariableExpression> iterator = this.args.iterator();
//                while(iterator.hasNext() && childExpression.hasNextChild()){
//                    VariableExpression exp = iterator.next();
//                    exp.valueExpression = childExpression.nextChild();  // 强制赋值, 这时候, variable expression可能是void型
//                }
            }
            return true;
        }

        @Override
        public ISpace<BaseNumber> interpret() {
            if(this.prototype.realVariable == this){
                return null;
            }else{
//                if(this.args != null){
//                    Iterator<VariableExpression> iterator = this.args.iterator();
//                    while(iterator.hasNext()){
//                        iterator.next().interpret();
//                    }
//                }
                return this.prototype.realVariable.valueExpression.interpret();
            }
        }

//        @Override
//        public VariableExpression clone() {
//            FunctionVariableExpression functionVariableExpression = new FunctionVariableExpression(this.symbol, this.prototype);
//
//            return functionVariableExpression;
//        }
    }

    /**
     * 值变量表达式
     */
    private static class ValueVariableExpression extends VariableExpression{

        public ValueVariableExpression(String symbol, VariableExpression prototype) {
            super(symbol, prototype);
        }

        @Override
        public boolean createBranch(IExpression childExpression) {
            if(childExpression.isLeaf() && this.prototype.assign.equals(childExpression.symbol()) && this.valueExpression == null){   // 只有叶子节点才可能作为变量表达式的子表达式
                this.valueExpression = childExpression;
                return true;
            }
            return false;
        }

        @Override
        public ISpace<BaseNumber> interpret() {
            ISpace<BaseNumber> result;
            if(this.valueExpression != null){ // 重新赋值
                result = this.valueExpression.interpret();
            }else if(this.prototype.value != null){ // 没有重新赋值, 存在默认值
                result = this.prototype.value;
            }else {
                throw new IllegalStateException("variable " + this.symbol + " is not assign.");
            }
            this.prototype.value = result;
            return result;
        }
    }

    /**
     * 泛型变量, 类型还未确定的变量
     */
    private static class VoidVariableExpression extends VariableExpression {

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
                    this.prototype.realVariable = this.prototype.clone();
                    if(this.prototype.realVariable.createBranch(childExpression)){
                        return true;
                    }else{
                        this.prototype.realVariable = null;
                        return false;
                    }
                }else{
                    return this.prototype.realVariable.createBranch(childExpression);
                }
            }
            return false;
        }
    }

    /**
     * 形参变量表达式
     */
    private static class FormatArgumentExpression extends VariableExpression{

        private FunctionVariableExpression functionVariableExpression;

        public FormatArgumentExpression(String symbol, VariableExpression prototype) {
            super(symbol, prototype);
        }

    }
}
