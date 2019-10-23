package frog.calculator.util;

import frog.calculator.util.collection.ITraveller;

public class Arrays {

    public static <T> void copy(T[] source, T[] dest, int start, int end){
        checkCopy(source.length, dest.length, start, end);

        for(int i = 0, j = start; j <= end; j++, i++){
            dest[i] = source[j];
        }
    }

    public static void copy(int[] source, int[] dest, int start, int end){
        checkCopy(source.length, dest.length, start, end);
        for(int i = 0, j = start; j < end; j++, i++){
            dest[i] = source[j];
        }
    }

    private static void checkCopy(int sourceLen, int destLen, int start, int end){
        if(start < 0 || end > sourceLen || start > end){
            throw new IllegalArgumentException("start and end index is error. start : " + start + ", end : " + end);
        }
        if(end - start > destLen){
            throw new IllegalArgumentException("desc array is not enough. desc's length : " + destLen + ", will put : " + (end - start + 1));
        }
    }

    public static ITraveller<Integer> traveller(int[] array){
        return new IntegerArrayTraveller(array);
    }

    public static <T> ITraveller<T> traveller(T[] array){
        return new ArrayTraveller<>(array);
    }

    private static class ArrayTraveller<E> implements ITraveller<E> {

        private E[] array;

        private int i = -1;

        public ArrayTraveller(E[] array) {
            this.array = array;
        }

        @Override
        public boolean hasNext() {
            return i + 1 < array.length;
        }

        @Override
        public E next() {
            return this.array[++i];
        }
    }


    private static class IntegerArrayTraveller implements ITraveller<Integer> {

        private int[] array;

        private int i = -1;

        public IntegerArrayTraveller(int[] array) {
            this.array = array;
        }

        @Override
        public boolean hasNext() {
            return i + 1 < array.length;
        }

        @Override
        public Integer next() {
            return this.array[++i];
        }
    }
}
