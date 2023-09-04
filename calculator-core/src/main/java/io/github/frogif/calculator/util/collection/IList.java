package io.github.frogif.calculator.util.collection;

public interface IList<E> extends ICollection<E> {

    void add(int index, E element);

    E remove(int index);

    E get(int index);

    int indexOf(E e);
}
