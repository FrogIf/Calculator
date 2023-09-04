package io.github.frogif.calculator.util.collection;

public interface IMap<K, V> {

    /**
     * 根据key获取值
     * @param key
     * @return
     */
    V get(K key);

    /**
     * 将一对键值放入map
     * @param key
     * @param value
     * @return
     */
    V put(K key, V value);

    /**
     * 映射个数
     * @return
     */
    int size();

    /**
     * 该map是否为空
     * @return
     */
    boolean isEmpty();

    /**
     * map中是否包含某个key
     * @param key
     * @return
     */
    boolean containsKey(K key);

    /**
     * 删除某个key
     * @param key
     * @return 是否移除成功
     */
    boolean remove(K key);

    /**
     * 清空map
     */
    void clear();

    /**
     * 获取映射的集合
     * @return
     */
    ISet<Entry<K, V>> entrySet();

    interface Entry<K, V>{
        /**
         * 获取key
         * @return
         */
        K getKey();

        /**
         * 获取value
         * @return
         */
        V getValue();

        /**
         * 设置value
         * @param value
         */
        void setValue(V value);
    }
}
