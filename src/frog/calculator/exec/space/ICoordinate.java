package frog.calculator.exec.space;

import frog.calculator.util.collection.ITraveller;

/**
 * 只支持正数轴
 */
public interface ICoordinate {

    void add(int axialValue);

    int dimension();

    ITraveller<Integer> traveller();

    /**
     * 删除右侧的坐标, 直到遇见第一个不为0的数字
     */
    void trimRight();

    void clear();
}
