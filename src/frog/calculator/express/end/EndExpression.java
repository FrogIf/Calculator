package frog.calculator.express.end;

import frog.calculator.express.IExpression;
import frog.calculator.express.PriorityExpression;

public abstract class EndExpression extends PriorityExpression {

    @Override
    public int priority() {
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
}
