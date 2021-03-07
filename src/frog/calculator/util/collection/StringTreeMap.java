package frog.calculator.util.collection;

import frog.calculator.util.IComparator;

public class StringTreeMap<V> implements IMap<String, V>{

    private final EntryHolder<V> root = new EntryHolder<>();

    @Override
    public V get(String key) {
        EntryHolder<V> holder = root.retrieve(key);
        if(holder != null && holder.entry != null){
            return holder.entry.value;
        }else{
            return null;
        }
    }

    @Override
    public void put(String key, V value) {

    }

    public static class Entry<V> {
        private String key;

        private V value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }

    public static class EntryHolder<V>{

        private char mark = 0;

        private Entry<V> entry;

        private final RBTreeSet<EntryHolder<V>> register = new RBTreeSet<>(new IComparator<EntryHolder<V>>() {
            @Override
            public int compare(EntryHolder<V> a, EntryHolder<V> b) {
                return a.mark - b.mark;
            }
        });

        public void insertOrReplace(Entry<V> entry){
            String key = entry.key;
        }

        public EntryHolder<V> retrieve(String key) {
            char[] chars = key.toCharArray();
            int startIndex = 0;

            EntryHolder<V> finder = new EntryHolder<>();
            finder.mark = chars[0];
            EntryHolder<V> h = register.find(finder);
            if(h != null){
                return h.retrieve(chars, 1, finder);
            }else{
                return null;
            }
        }

        private EntryHolder<V> retrieve(char[] key, int index, EntryHolder<V> finder){
            if(index < key.length){
                char ch = key[index];
                finder.mark = ch;
                return retrieve(key, index + 1, finder);
            }else{
                return this;
            }
        }


    }
}
