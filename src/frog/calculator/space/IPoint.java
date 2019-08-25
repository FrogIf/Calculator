package frog.calculator.space;

public interface IPoint<E> extends Cloneable, Comparable<IPoint<E>> {

    /**
     * @return return string value.
     */
    E intrinsic();

    ICoordinate getCoordinate();

    IPoint<E> clone();

}
