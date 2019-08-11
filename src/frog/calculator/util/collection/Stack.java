package frog.calculator.util.collection;

/**
 * simple stack
 * @param <E>
 */
public class Stack<E> extends LinkedList<E> {

    public E top(){
        return this.first();
    }

    public E pop(){
        return this.preRemove();
    }

    public void push(E e){
        this.preInsert(e);
    }

}
