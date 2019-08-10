package frog.calculator.util;

public interface ISet<E> {

    int size();

    boolean isEmpty();

    E find(E e);

    Iterator<E> iterator();

    boolean add(E e);

    void clear();

    boolean remove(E e);
}
