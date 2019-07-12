package frog.calculator.express.result;

import frog.calculator.express.ExpressionType;
import frog.calculator.express.IExpression;
import frog.calculator.operater.IOperator;

public abstract class AResultExpression implements IExpression {

    public String resultValue(){
        return this.strValue;
    }

    protected String strValue;

    @Override
    public boolean createBranch(IExpression expression) {
        return false;
    }

    @Override
    public IExpression assembleTree(IExpression expression) {
        return null;
    }

    @Override
    public IExpression clone() {
        try {
            return (IExpression) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public final ExpressionType type(){
        return ExpressionType.TERMINAL;
    }

    @Override
    public AResultExpression interpret() {
        return this;
    }

    @Override
    public int priority() {
        return 0;
    }


    @Override
    public IOperator getOperator() {
        return null;
    }

}
