package frog.calculator.util;

public interface IComparator<T> {

    public static final IComparator<String> STRING_DEFAULT_COMPARATOR = (a, b) -> a.compareTo(b);

    int compare(T a, T b);

}
