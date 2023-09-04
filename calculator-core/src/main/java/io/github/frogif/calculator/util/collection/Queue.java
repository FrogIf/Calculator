package io.github.frogif.calculator.util.collection;

public class Queue<E> extends LinkedList<E>{

    public void enqueue(E e){
        this.add(e);
    }

    public E dequeue(){
        return this.preRemove();
    }
}
