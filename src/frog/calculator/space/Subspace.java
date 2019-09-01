package frog.calculator.space;

import frog.calculator.util.collection.IList;

public class Subspace<T> implements ISpace<T> {

    @Override
    public int dimension() {
        return 0;
    }

    @Override
    public int width(ICoordinate coordinate) {
        return 0;
    }

    @Override
    public void addPoint(IPoint<T> point, ICoordinate coordinate) {

    }

    @Override
    public IList<IPoint<T>> getPoints() {
        return null;
    }

    @Override
    public IPoint<T> getPoint(ICoordinate coordinate) {
        return null;
    }

    @Override
    public ISpace<T> getSubspace(ICoordinate coordinate) {
        return null;
    }
}
