package frog.calculator.util.collection;

public interface ICollection<E> {

    /**
     * 集合中添加新的元素
     * @param e 待添加的元素
     * @return 是否成功添加
     */
    boolean add(E e);

    /**
     * 将一个集合中所有元素添加到另一个集合
     * @param collection 待添加元素所在集合
     */
    void addAll(ICollection<E> collection);

    /**
     * 从集合中移除指定的元素
     * @param e 需移除的元素
     * @return 返回是否成功移除
     */
    boolean remove(E e);

    /**
     * 清空集合
     */
    void clear();

    /**
     * 判断集合是否为空
     * @return true 空集, false 非空集
     */
    boolean isEmpty();

    /**
     * 获取集合中元素个数
     * @return 集合个数
     */
    int size();

    /**
     * 获取集合的迭代器
     * @return 迭代器
     */
    Iterator<E> iterator();

    /**
     * 查找集合中某一个元素
     * @param key 指定关键字
     * @return 该元素
     */
    E find(E key);

    /**
     * 判断集合中是否包含某一指定元素
     * @param e 待判断的元素
     * @return true 包含, false 不包含
     */
    boolean contains(E e);
}
