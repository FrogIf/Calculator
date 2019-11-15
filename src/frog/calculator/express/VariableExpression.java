package frog.calculator.express;

import frog.calculator.exception.ArgumentUnmatchException;
import frog.calculator.exec.space.ISpace;
import frog.calculator.math.BaseNumber;

/**
 * 值变量表达式
 */
public class VariableExpression extends EndPointExpression {

    private PrototypeVariableExpression prototype;

    private IExpression value;

    private IExpression actualArg;

    private IExpression formatArg;

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
        if(childExpression.isLeaf()){
            if(this.assign.equals(childExpression.symbol())){
                if(this.value == null && this.prototype.argumentList == null){
                    this.value = childExpression;
                        this.prototype.funBody = childExpression;
                    return true;
                }else{
                    return false;
                }
            }else if(this.prototype.protoValue == null && this.prototype.argumentList == null){ // 说明该变量未初始化
                this.prototype.argumentList = childExpression;
                return true;
            }else if(this.prototype.argumentList != null && this.actualArg == null){
                this.actualArg = childExpression;
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
        if(this.actualArg != null && this.formatArg != null){  // 函数
            IExpression aArgList = this.actualArg.nextChild();
            IExpression fArgList = this.formatArg.nextChild();
            if(aArgList != null && fArgList != null){
                while(aArgList.hasNextChild() && fArgList.hasNextChild()){
                    VariableExpression fArg = (VariableExpression) fArgList.nextChild();  // 形参
                    fArg.value = aArgList.nextChild();  // 实参
                }
                if(aArgList.hasNextChild() || fArgList.hasNextChild()){
                    throw new ArgumentUnmatchException(this.symbol);
                }
            }else if(aArgList != null || fArgList != null){
                throw new ArgumentUnmatchException(this.symbol);
            }
            return this.value.interpret();
        }else if(this.actualArg == null && this.prototype.argumentList == null){
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
        }else{
            throw new ArgumentUnmatchException(this.symbol);
        }
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

        private IExpression funBody;

        // 参数列表
        private IExpression argumentList;

        @Override
        public IExpression clone() {
            VariableExpression variableExpression = new VariableExpression(this.symbol, this.assign);
            variableExpression.value = null;
            variableExpression.prototype = this;
            if(this.argumentList != null) {
                variableExpression.formatArg = this.argumentList.clone();
            }
            return variableExpression;
        }
    }
}
