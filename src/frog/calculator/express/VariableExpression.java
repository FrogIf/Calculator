package frog.calculator.express;

import frog.calculator.exception.ArgumentUnmatchException;
import frog.calculator.exec.space.ISpace;
import frog.calculator.math.BaseNumber;
import frog.calculator.util.collection.IList;
import frog.calculator.util.collection.Iterator;
import frog.calculator.util.collection.LinkedList;

/**
 * 值变量表达式
 */
public class VariableExpression extends EndPointExpression {

    private PrototypeVariableExpression prototype;

    private IExpression value;

    private IExpression actualArg;

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
        if(childExpression.isLeaf()){   // 只有叶子节点才可能作为变量表达式的子表达式
            if(this.assign.equals(childExpression.symbol())){   // 如果是赋值操作符
                if(this.value == null && this.prototype.argumentList == null){
                    this.value = childExpression;
                    return true;
                }else if(this.value == null){   // 暗示argumentList不为null, 构造funBody
                    Iterator<PrototypeVariableExpression> iterator = this.prototype.argumentList.iterator();
                    while (iterator.hasNext()){
                        iterator.next().funRef = this.prototype;
                    }
                    this.prototype.funBody = childExpression.clone();   // 这里触发clone, 与signRef配合(参见clone方法), 将内部变量全部替换为变量原型
                    return true;
                }else{
                    return false;
                }
            }else if(this.prototype.argumentList == null){ // 说明该变量未初始化
                this.prototype.argumentList = new LinkedList<>();
                while(childExpression.hasNextChild()){
                    IExpression expression = childExpression.nextChild();
                    if(expression instanceof VariableExpression){
                        this.prototype.argumentList.add(((VariableExpression) expression).prototype);
                    }else{
                        this.prototype.argumentList = null;
                        return false;
                    }
                }
                return true;
            }else if(this.actualArg == null){    // 说明这是一个函数变量, 并且还没指定实参
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
        if(this.prototype.funBody != null && this.prototype.argumentList != null){  // 函数变量
            // TODO 函数运算
            return this.value.interpret();
        }else if(this.prototype.argumentList == null){    // 值变量
            ISpace<BaseNumber> result;
            if(this.value != null){ // 重新赋值
                result = this.value.interpret();
            }else if(prototype.protoValue != null){ // 没有重新赋值, 存在默认值
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
        return this.prototype.funRef == null ? this : this.prototype;
    }

    private static class PrototypeVariableExpression extends VariableExpression {

        private PrototypeVariableExpression(String symbol, String assign) {
            super(symbol, assign);
        }

        private ISpace<BaseNumber> protoValue;

        private IExpression funBody;

        // 参数列表
        private IList<PrototypeVariableExpression> argumentList;

        // 这个参数是为函数形参提供的, 每一个形参都关联与其对应的函数变量
        private PrototypeVariableExpression funRef;

        @Override
        public IExpression clone() {
            // TODO copy改造
            VariableExpression variableExpression = new VariableExpression(this.symbol, this.assign);
            variableExpression.value = null;
            variableExpression.prototype = this;
            return variableExpression;
        }
    }

}
