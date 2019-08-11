package frog.calculator.util.collection;

public interface Iterator<E> {

    boolean hasNext();

    E next();

    void remove();
}
