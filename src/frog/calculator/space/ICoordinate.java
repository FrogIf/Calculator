package frog.calculator.space;

import frog.calculator.util.collection.Itraveller;

/**
 * 只支持正数轴
 */
public interface ICoordinate {

    void add(int axialValue);

    int dimension();

    Itraveller<Integer> traveller();

    /**
     * 判断是否是原点
     * @return
     */
    boolean isOrigin();
}