package sch.frog.calculator.util.collection;

public class SingleElementTraveller<E> implements ITraveller<E> {

    private E element;

    private boolean hasNext = true;

    public SingleElementTraveller(E element) {
        this.element = element;
    }

    @Override
    public boolean hasNext() {
        return hasNext;
    }

    @Override
    public E next() {
        if(!hasNext) {
            return null;
        } else {
            hasNext = false;
            return element;
        }
    }
}
