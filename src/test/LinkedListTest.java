package test;

import frog.calculator.util.collection.IList;
import frog.calculator.util.collection.LinkedList;

public class LinkedListTest {
    public static void main(String[] args){
        IList<Integer> list = new LinkedList<>();

        list.add(12);
        list.add(12);
        list.add(12);
        list.add(12);
        list.add(12);
        list.add(12);
        list.add(12);
        list.add(12);
        list.add(12);

        System.out.println(list.size());
    }
}
