package frog.calculator.space;

public interface IPoint<E> extends Cloneable {

    /**
     * @return return string value.
     */
    E intrinsic();

    int getAxialValue();

    void setAxialValue(int val);

//    ICoordinate getCoordinate();

    IPoint<E> clone();

}
