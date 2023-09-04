package io.github.frogif.calculator.util;

public class ComparableComparator<T extends Comparable<T>> implements IComparator<T> {

    private ComparableComparator(){}

    private static final ComparableComparator INSTANCE = new ComparableComparator<>();

    @SuppressWarnings("unchecked")
    public static <K extends Comparable<K>> ComparableComparator<K> getInstance(){
        return INSTANCE;
    }

    @Override
    public int compare(T a, T b) {
        return a.compareTo(b);
    }
}
