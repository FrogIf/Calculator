package frog.calculator.util;

/**
 * simple stack
 * @param <T>
 */
public class Stack<T> {

    private Node top;

    private Node bottom;

    public boolean isEmpty(){
        return top == null;
    }

    public T top(){
        return top == null ? null : top.data;
    }

    public void clear(){
        top = null;
        bottom = null;
    }

    public T pop(){
        T result = top.data;
        top = top.next;
        if(top == null){ bottom = null; }
        return result;
    }

    public void push(T t){
        Node newTop = new Node(t);
        newTop.next = top;
        top = newTop;
        if(bottom == null) { bottom = newTop; }
    }

    private class Node {
        private Node(T t){
            this.data = t;
        }
        private T data;
        private Node next;
    }

}
