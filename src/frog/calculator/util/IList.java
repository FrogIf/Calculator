package frog.calculator.util;

public interface IList<E extends Comparable<E>> {

    int size();

    boolean isEmpty();

    boolean add(E e);

    void add(int index, E element);

    boolean contains(E e);

    Iterator<E> iterator();

    void clear();

    E get(int index);

    E get(E e);

    boolean remove(E e);

    E remove(int index);
}
