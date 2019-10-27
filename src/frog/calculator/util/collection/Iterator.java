package frog.calculator.util.collection;

public interface Iterator<E> extends ITraveller<E> {
    /**
     * 从迭代对象中删除当前迭代到的元素
     */
    void remove();
}
