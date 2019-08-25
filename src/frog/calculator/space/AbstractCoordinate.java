package frog.calculator.space;

import frog.calculator.util.collection.Itraveller;

public abstract class AbstractCoordinate implements ICoordinate {
    public static final ICoordinate EMPTY = new ICoordinate() {
        @Override
        public void add(int axialValue) {
            throw new IllegalArgumentException("the coordinate can't modify.");
        }

        @Override
        public int dimension() {
            return 0;
        }

        @Override
        public Itraveller<Integer> traveller() {
            return null;
        }
    };
}
