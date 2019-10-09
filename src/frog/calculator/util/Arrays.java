package frog.calculator.util;

import frog.calculator.util.collection.ITraveller;

public class Arrays {

    public static <T> T[] copy(T[] source, T[] desc, int start, int end){
        if(start < 0 || (start > source.length - 1) || (end > source.length - 1) || start > end){
            throw new IllegalArgumentException("start and end index is error. start : " + start + ", end : " + end);
        }
        if(end - start + 1 > desc.length){
            throw new IllegalArgumentException("desc array is not enough. desc's length : " + desc.length + ", will put : " + (end - start + 1));
        }

        for(int i = 0, j = start; j <= end; j++, i++){
            desc[i] = source[j];
        }

        return desc;
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
