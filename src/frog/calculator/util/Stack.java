package frog.calculator.util;

/**
 * 简易栈结构
 * @param <T>
 */
public class Stack<T> {

    private Node top;   // 栈顶

    /**
     * 栈中是否为空
     * @return
     */
    public boolean isEmpty(){
        return top == null;
    }

    public boolean isNotEmpty(){
        return top != null;
    }

    public T getTop(){
        return top.data;
    }

    /**
     * 出栈
     * @return
     */
    public T pop(){
        T result = top.data;
        top = top.next;
        return result;
    }

    /**
     * 入栈
     * @param t
     */
    public void push(T t){
        Node newTop = new Node(t);
        newTop.next = top;
        top = newTop;
    }

    private class Node{
        private Node(T t){
            this.data = t;
        }
        private T data;
        private Node next;
    }

}
