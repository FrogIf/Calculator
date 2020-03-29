package frog.calculator.execute.space;

import frog.calculator.util.collection.ITraveller;

public abstract class AbstractCoordinate implements ICoordinate {
    public static final ICoordinate ORIGIN = new ICoordinate() {
        @Override
        public void add(int axialValue) {
            throw new IllegalArgumentException("the coordinate can't modify.");
        }

        @Override
        public int dimension() {
            return 0;
        }

        @Override
        public ITraveller<Integer> traveller() {
            return new ITraveller<Integer>() {
                @Override
                public boolean hasNext() {
                    return false;
                }

                @Override
                public Integer next() {
                    return null;
                }
            };
        }

        @Override
        public void trimRight() {
            // do nothing
        }

        @Override
        public void clear() {
            // do nothing
        }
    };
}
