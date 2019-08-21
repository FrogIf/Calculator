package test;

import frog.calculator.util.collection.ArrayList;
import frog.calculator.util.collection.IList;
import frog.calculator.util.collection.Iterator;

public class ArrayListTest {

    public static void main(String[] args){
        System.out.println("-----------");
        customArrayList();
    }

    private static void customArrayList(){
        IList<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(1);
        list.add(2);
        Iterator<Integer> iterator = list.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }

        System.out.println("-------------");
        list.add(3, 3);

        iterator = list.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }


    }

}
