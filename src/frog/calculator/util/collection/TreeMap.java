package frog.calculator.util.collection;

import frog.calculator.util.IComparator;

public class TreeMap<K, V> implements IMap<K, V> {

    private final RBTreeSet<Entry<K, V>> entryHolder = new RBTreeSet<>(new IComparator<Entry<K, V>>(){
        @Override
        public int compare(Entry<K, V> a, Entry<K, V> b) {
            return a.key.hashCode() - b.key.hashCode();
        }

    });

    public static class Entry<K, V> {
        private K key;

        private V value;

        public V getValue() {
            return value;
        }

        public K getKey(){
            return this.key;
        }
    }

    @Override
    public V get(K key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void put(K key, V value) {
        // TODO Auto-generated method stub

    }
}
