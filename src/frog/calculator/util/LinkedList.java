package frog.calculator.util;

/**
 * 链表<br/>
 * 线程不安全
 * @param <T>
 */
public class LinkedList<T>{

    private LinkedNode<T> root = null;

    private LinkedNode<T> tail = null;

    private int size = 0;

    public void add(T t){
        if(root == null){
            root = new LinkedNode<>();
            tail = root;
        }
        tail.next = new LinkedNode<>();
        tail.next.data = t;
        tail = tail.next;
        size++;
    }

    public int size(){
        return size;
    }

    public Iterator getIterator(){
        return new Iterator(root);
    }

    public class Iterator{
        private LinkedNode<T> viewNode;

        public Iterator(LinkedNode<T> viewNode) {
            this.viewNode = viewNode;
        }

        public boolean hasNext(){
            return viewNode != null && viewNode.next != null;
        }

        public T next(){
            this.viewNode = viewNode.next;
            return this.viewNode.data;
        }
    }

    private static class LinkedNode<T>{
        private LinkedNode<T> next;
        private T data;
    }

}
