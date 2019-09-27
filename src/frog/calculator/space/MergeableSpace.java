package frog.calculator.space;

public abstract class MergeableSpace<T> implements ISpace<T> {

    public abstract void addSubspace(ICoordinate coordinate, ISpace<T> space);

}