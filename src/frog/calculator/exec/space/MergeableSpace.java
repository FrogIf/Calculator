package frog.calculator.exec.space;

public abstract class MergeableSpace<T> implements ISpace<T> {

    public abstract void addSubspace(ICoordinate coordinate, ISpace<T> space);

}
