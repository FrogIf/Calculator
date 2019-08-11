package frog.calculator.util.collection;

public interface IList<E> extends ICollection<E> {

    void add(int index, E element);

    E get(int index);

    E remove(int index);

    int indexOf(E e);
}
