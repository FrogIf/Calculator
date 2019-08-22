package frog.calculator.space;

import frog.calculator.util.collection.IList;

public interface ISpace {

    /**
     * get value by coordinate.
     * @param coordinate
     * @return
     */
    ILiteral getValue(ICoordinate coordinate);

    /**
     * dimension <br />
     * the minimum value is 0.
     * if the literal is a number. dimension is 1.
     * if the literal is a array. dimension is 1.
     * if the literal is a vector. dimension is 1.
     * if the literal is a matrix. dimension is 2.
     * @return a value represent dimension
     */
    int dimension();

    /**
     * get width by assign dimension.
     * @param dimension
     * @return
     */
    int width(int dimension);

    /**
     * add value by assign coordinate.
     * @param coordinate
     * @param literal
     */
    void addValue(ICoordinate coordinate, ILiteral literal);

    /**
     * 获取所有的值
     * @return
     */
    IList<ILiteral> getValues();

    IList<ISpace> getSubspaces();

}
