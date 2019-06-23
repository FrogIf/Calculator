package frog.calculator.expression.end;

import frog.calculator.expression.IExpression;
import frog.calculator.expression.PriorityExpression;

public abstract class EndExpression extends PriorityExpression {

    public abstract double interpret();

    @Override
    public int getPriority() {
        return 100;
    }

    @Override
    public boolean createBranch(IExpression expression) {
        return false;
    }

    @Override
    public boolean isLeaf(){
        return true;
    }

    @Override
    public IExpression assembleTree(IExpression expression){
        if(expression.isLeaf()){
            // 两个叶子不能组装到一起
            return null;
        }else{
            return expression.assembleTree(this);
        }
    }

    @Override
    public IExpression[] branches() {
        return new IExpression[0];
    }
}
