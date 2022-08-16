package sch.frog.calculator.util.collection;

import sch.frog.calculator.util.IComparator;

public class TreeMap<K, V> implements IMap<K, V> {

    private final RBTreeSet<Entry<K, V>> entrySet;

    public TreeMap(IComparator<K> comparator){
        this.entrySet = new RBTreeSet<>(new IComparator<Entry<K, V>>(){
            @Override
            public int compare(Entry<K, V> a, Entry<K, V> b) {
                return comparator.compare(a.getKey(), b.getKey());
            }
        });
    }

    @Override
    public V get(K key) {
        InnerEntry<K, V> finder = new InnerEntry<>();
        finder.key = key;
        Entry<K, V> entry = this.entrySet.find(finder);
        if(entry != null){
            return entry.getValue();
        }else{
            return null;
        }
    }

    @Override
    public V put(K key, V value) {
        if(key == null){
            throw new IllegalArgumentException("key can't be null.");
        }
        InnerEntry<K, V> entry = new InnerEntry<>();
        entry.key = key;
        entry.value = value;
        this.entrySet.replace(entry);
        return value;
    }

    @Override
    public int size() {
        return entrySet.size();
    }

    @Override
    public boolean isEmpty() {
        return entrySet.size() == 0;
    }

    @Override
    public boolean containsKey(K key) {
        InnerEntry<K, V> finder = new InnerEntry<>();
        finder.key = key;
        return this.entrySet.contains(finder);
    }

    @Override
    public boolean remove(K key) {
        InnerEntry<K, V> finder = new InnerEntry<>();
        finder.key = key;
        return entrySet.remove(finder);
    }

    @Override
    public void clear() {
        entrySet.clear();
    }

    @Override
    public ISet<Entry<K, V>> entrySet() {
        return this.entrySet;
    }

    private static class InnerEntry<K, V> implements Entry<K, V> {

        private K key;

        private V value;

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public void setValue(V value) {
            this.value = value;            
        }
        
    }

}
