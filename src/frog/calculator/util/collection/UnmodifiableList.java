package frog.calculator.util.collection;

public class UnmodifiableList<E> implements IList<E> {

    private final IList<E> list;

    public UnmodifiableList(IList<E> list) {
        this.list = list;
    }

    @Override
    public boolean add(E e) {
        throw new UnsupportedOperationException("this list can't be modify.");
    }

    @Override
    public void add(int index, E element) {
        throw new UnsupportedOperationException("this list can't be modify.");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("this list can't be modify.");
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    @Override
    public E find(E e) {
        return list.find(e);
    }

    @Override
    public boolean contains(E e) {
        return list.contains(e);
    }

    @Override
    public boolean remove(E e) {
        throw new UnsupportedOperationException("this list can't be modify.");
    }

    @Override
    public E remove(int index) {
        throw new UnsupportedOperationException("this list can't be modify.");
    }

    @Override
    public E get(int index) {
        return list.get(index);
    }

    @Override
    public int indexOf(E e) {
        return list.indexOf(e);
    }

    private class Itr implements Iterator<E>{

        Iterator<E> itr = UnmodifiableList.this.list.iterator();

        @Override
        public boolean hasNext() {
            return itr.hasNext();
        }

        @Override
        public E next() {
            return itr.next();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("this list can't be modify.");
        }
    }
}
