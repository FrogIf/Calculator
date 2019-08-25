package frog.calculator.space;

import frog.calculator.util.collection.IList;

public interface ISpace<T> {

    int dimension();

    int width(ICoordinate coordinate);

    void addPoint(IPoint<T> point);

    IList<IPoint<T>> getPoints();

    IPoint<T> getPoint(ICoordinate coordinate);

    /**
     * get subspace where assign coordinate is in.
     * @param coordinate aim coordinate
     * @return coordinate's space
     */
    ISpace<T> getSubspace(ICoordinate coordinate);

    ISpace<T> getNextLevelSubspace(int index);

}
