package frog.calculator.express;

public abstract class AbstractBuildableExpression implements IExpression{
    @Override
    public IExpression clone() {
        try {
            return (IExpression) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
