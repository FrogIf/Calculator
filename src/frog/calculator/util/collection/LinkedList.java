package frog.calculator.util.collection;

/**
 * 链表<br/>
 * @param <E>
 */
public class LinkedList<E> implements IList<E>{

    private Node<E> head = new Node<>();    // head is empty node. can't get.

    private Node<E> tail;

    private int modCount = 0;

    public void preInsert(E e){
        Node<E> node = new Node<>();
        node.item = e;
        node.next = head.next;
        head.next = node;
        modCount++;
    }

    public void postInsert(E t){
        add(t);
    }

    public E preRemove(){
        Node<E> top = head.next;
        head.next = top.next;
        modCount--;
        return top.item;
    }

    public E postRemove(){
        if(head.next == null){ return null; }

        Node<E> cursor = head;
        while(cursor.next.next != null){
            cursor = cursor.next;
        }

        E e = cursor.next.item;
        cursor.next = null;

        modCount--;
        return e;
    }

    public E first(){
        return head.next == null ? null : head.next.item;
    }

    public E last(){
        return tail == null ? null : tail.item;
    }

    @Override
    public boolean add(E t){
        if(tail == null){
            tail = head;
        }
        tail.next = new Node<>();
        tail = tail.next;
        tail.item = t;
        modCount++;
        return true;
    }

    @Override
    public void add(int index, E e) {
        if(index >= modCount){
            this.add(e);
        }else {
            Node<E> cursor = head;
            for(int i = 0; i < index; i++){
                cursor = cursor.next;
            }
            Node<E> node = new Node<>();
            node.item = e;
            node.next = cursor.next;
            cursor.next = node;
            modCount++;
        }
    }

    @Override
    public boolean contains(E e) {
        return this.indexOf(e) >= 0;
    }

    @Override
    public boolean isEmpty() {
        return modCount == 0;
    }

    @Override
    public int size(){
        return modCount;
    }

    @Override
    public Iterator<E> iterator() {
        return new LinkedIterator();
    }

    @Override
    public void clear() {
        this.head.next = null;
        this.tail = null;
        this.modCount = 0;
    }

    @Override
    public E get(int index) {
        if(index >= modCount){
            throw new IllegalArgumentException("index out of bounds.");
        }else if(index >= 0){
            Node<E> cursor = head.next;
            for(int i = 0; i < index; i++){
                cursor = cursor.next;
            }
            return cursor.item;
        }else{
            return null;
        }
    }

    @Override
    public E find(E e) {
        if(e == null){
            return null;
        }else{
            Node<E> cursor = head.next;
            while(cursor != null){
                if(e.equals(cursor.item)){
                    return cursor.item;
                }
                cursor = cursor.next;
            }
        }
        return null;
    }

    @Override
    public boolean remove(E e) {
        Node<E> cursor = head.next;
        Node<E> pre = head;
        if(e == null){
            while(cursor != null){
                if(cursor.item == null){
                    pre.next = cursor.next;
                    return true;
                }
                pre = cursor;
                cursor = cursor.next;
            }
        }else{
            while(cursor != null){
                if(e.equals(cursor.item)){
                    pre.next = cursor.next;
                    return true;
                }
                pre = cursor;
                cursor = cursor.next;
            }
        }
        return false;
    }

    @Override
    public E remove(int index) {
        if(index >= modCount){
            return null;
        }else{
            Node<E> cursor = head.next;
            for(int i = 0; i < index; i++){
                cursor = cursor.next;
            }
            return cursor.item;
        }
    }

    @Override
    public int indexOf(E t) {
        Node cursor = head.next;
        int index = 0;
        if(t == null){
            while(cursor != null){
                if(cursor.item == null){ return index; }
                index++;
                cursor = cursor.next;
            }
        }else{
            while(cursor != null){
                if(t.equals(cursor.item)){ return index; }
                index++;
                cursor = cursor.next;
            }
        }
        return -1;
    }

    public void join(LinkedList<E> list){
        this.tail.next = list.head.next;
    }

    private class LinkedIterator implements Iterator<E> {

        private Node<E> pre;

        private Node<E> view;

        private int expectedModCount = modCount;

        private LinkedIterator() {
            pre = view = LinkedList.this.head;
        }

        @Override
        public boolean hasNext(){
            return view.next != null;
        }

        @Override
        public E next(){
            pre = view;
            view = view.next;
            return view.item;
        }

        @Override
        public void remove() {
            if(expectedModCount != modCount){
                throw new IllegalStateException("concurrent modify exception.");
            }

            pre.next = view.next;
            view = pre;
            expectedModCount--;
            modCount--;
        }
    }

    private static class Node<T>{
        private Node<T> next;
        private T item;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        if(this.size() > 0){
            Iterator<E> iterator = this.iterator();
            E next = iterator.next();
            sb.append(next);
            while(iterator.hasNext()){
                sb.append(',');
                sb.append(iterator.next());
            }
        }
        sb.append(']');
        return sb.toString();
    }
}
