package frog.calculator.space;

import frog.calculator.util.collection.Iterator;

public class CommonCoordinateViewer implements ICoordinateViewer {

    private Iterator<Integer> iterator;

    public CommonCoordinateViewer(Iterator<Integer> iterator) {
        this.iterator = iterator;
    }

    @Override
    public boolean hasNextAxis() {
        return iterator.hasNext();
    }

    @Override
    public int nextAxialValue() {
        return iterator.next();
    }
}
