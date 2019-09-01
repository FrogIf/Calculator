package frog.calculator.space;

public abstract class AbstractPoint<T> implements IPoint<T> {

    protected ICoordinate coordinate;

    protected int axialValue;

    @Override
    public IPoint clone() {
        Object clone = null;
        try {
            clone = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return (IPoint) clone;
    }
}
