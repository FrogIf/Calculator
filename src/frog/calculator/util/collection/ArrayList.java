package frog.calculator.util.collection;

import java.util.ConcurrentModificationException;

public class ArrayList<E> implements IList<E> {

    private static final int DEFAULT_CAPACITY = 10;

    private static final Object[] EMPTY_ARRAY = {};

    private int size;

    private Object[] array;

    public ArrayList(int initialCapacity) {
        if(initialCapacity > 0){
            array = new Object[initialCapacity];
        }else if(initialCapacity == 0){
            array = EMPTY_ARRAY;
        }else{
            throw new IllegalArgumentException("initialCapacity is negative.");
        }
    }

    public ArrayList() {
        array = new Object[DEFAULT_CAPACITY];
    }

    public ArrayList(E[] array){
        this.size = array.length;
        this.array = array;
    }

    @Override
    public boolean add(E e) {
        ensureCapacityInternal(size + 1);
        array[size++] = e;
        return true;
    }

    @Override
    public void add(int index, E element) {
        if(index < 0 || index > size){
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        ensureCapacityInternal(size + 1);

        for(int i = size - 1; i >= index; i--){
            array[i + 1] = array[i];
        }
        array[index] = element;
        size++;
    }

    private void ensureCapacityInternal(int needCapacity){
        if(needCapacity > array.length){
            Object[] newArr = new Object[array.length + (array.length >> 1)];
            for(int i = 0; i < array.length; i++){
                newArr[i] = array[i];
            }
            array = newArr;
        }
    }

    @Override
    public void clear() {
        size = 0;
        for(int i = 0; i < array.length; i++){
            array[i] = null;
        }
    }

    @Override
    public boolean remove(E e) {
        int pos = indexOf(e);
        if(pos >= 0){
            this.remove(pos);
            return true;
        }else{
            return false;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public E remove(int index) {
        if(index < 0 || index >= size){
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Object result = array[index];
        for(int i = index; i < size - 1; i++){
            array[i] = array[i + 1];
        }
        array[size--] = null;
        return (E) result;
    }

    @Override
    public int indexOf(E e) {
        if(e == null){
            for(int i = 0; i < size; i++){
                if(array[i] == null){
                    return i;
                }
            }
        }else{
            for(int i = 0; i < size; i++){
                if(e.equals(array[i])){
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E find(E e) {
        int i = indexOf(e);
        return i == -1 ? null : (E) array[i];
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E get(int index) {
        if(index >= size || index < 0){
            throw new ArrayIndexOutOfBoundsException(index);
        }else {
            return (E) array[index];
        }
    }

    @Override
    public boolean contains(E e) {
        return this.indexOf(e) >= 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<E> {

        private int expectedSize = size;

        private int cursor = 0;

        private int lastRet = -1;

        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        @Override
        @SuppressWarnings("unchecked")
        public E next() {
            if(size != expectedSize){
                throw new ConcurrentModificationException();
            }
            if(cursor >= size){
                throw new IndexOutOfBoundsException();
            }
            lastRet = cursor;
            return (E) array[cursor++];
        }

        @Override
        public void remove() {
            if(lastRet < 0){
                throw new IllegalStateException();
            }

            if(size != expectedSize){
                throw new ConcurrentModificationException();
            }

            ArrayList.this.remove(lastRet);
            cursor = lastRet;
            lastRet = -1;
            expectedSize--;
        }
    }

}
