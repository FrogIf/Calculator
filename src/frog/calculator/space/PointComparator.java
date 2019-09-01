package frog.calculator.space;

import frog.calculator.util.IComparator;

public class PointComparator<T> implements IComparator<IPoint<T>> {

    private PointComparator() {
    }

    private static final PointComparator INSTANCE = new PointComparator();

    @SuppressWarnings("unchecked")
    public static <K> PointComparator<K> getInstance(){
        return INSTANCE;
    }

    @Override
    public int compare(IPoint a, IPoint b) {
        return a.getAxialValue() - b.getAxialValue();
    }
}
