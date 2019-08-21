package frog.calculator.util.collection;

public interface ICollection<E> {

    boolean add(E e);

    boolean remove(E e);

    void clear();

    boolean isEmpty();

    int size();

    Iterator<E> iterator();

    E find(E e);

    boolean contains(E e);
}
