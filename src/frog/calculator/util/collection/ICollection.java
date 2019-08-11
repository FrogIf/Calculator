package frog.calculator.util.collection;

public interface ICollection<E> {

    boolean isEmpty();

    int size();

    Iterator<E> iterator();

    void clear();

    boolean add(E e);

    boolean remove(E e);

    E find(E e);

    boolean contains(E e);
}
