package sch.frog.calculator.util.collection;

public interface ISet<E> extends ICollection<E>{
    /**
     * 如果有, 则插入, 如果没有, 则替换
     * @param e
     */
    void replace(E e);
}
