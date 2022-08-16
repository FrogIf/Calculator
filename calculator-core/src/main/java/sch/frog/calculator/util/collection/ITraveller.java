package sch.frog.calculator.util.collection;

public interface ITraveller<E> {

    /**
     * 判断被迭代对象中是否存在下一个元素
     * @return true - 存在, false - 不存在
     */
    boolean hasNext();

    /**
     * 获取下一个元素
     * @return 下一个元素
     */
    E next();

}
