package frog.calculator.util.collection;

/**
 * simple stack
 * @param <E>
 */
public class Stack<E> extends LinkedList<E> {

    /**
     * 栈顶
     */
    public E top(){
        return this.first();
    }

    /**
     * 栈底
     * @return
     */
    public E bottom(){
        return this.last();
    }

    /**
     * 出栈
     */
    public E pop(){
        return this.preRemove();
    }

    /**
     * 入栈
     */
    public void push(E e){
        this.preInsert(e);
    }

}
