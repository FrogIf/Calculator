package frog.calculator.space;

import frog.calculator.util.collection.IList;

public interface ISpace<T> {

    /**
     * 获取空间的维度
     * @return
     */
    int dimension();

    /**
     * 该坐标点所在空间的空间宽度
     * @param coordinate
     * @return
     */
    int width(ICoordinate coordinate);

    /**
     * 向空间中添加点<br/>
     * @param point
     */
    void addPoint(IPoint<T> point, ICoordinate coordinate);

    /**
     * 获取该空间中包含的所有点, 按照坐标递增顺序输出<br/>
     * 所谓坐标递增顺序, 如果是三维, 则有: (0, 0, 0), (0, 0, 1), (0, 1, 0), (0, 1, 1)...
     * @return
     */
    IList<IPoint<T>> getPoints();

    /**
     * 根据指定坐标获取空间的点<br/>
     * 注意:<br/>
     * 如果坐标为(1), 空间维度为3, 那么会查找所有(1), (1, 0), (1, 0, 0), 直到找到或者到达空间的尽头为止
     * @param coordinate
     * @return
     */
    IPoint<T> getPoint(ICoordinate coordinate);

    /**
     * get subspace where assign coordinate is in.
     * @param coordinate aim coordinate
     * @return coordinate's space
     */
    ISpace<T> getSubspace(ICoordinate coordinate);

}
