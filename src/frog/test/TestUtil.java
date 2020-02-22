package frog.test;

import frog.calculator.util.collection.IList;
import frog.calculator.util.collection.Iterator;

public class TestUtil {

    public static void showList(IList list){
        Iterator iterator = list.iterator();
        System.out.print("(");
        if(iterator.hasNext()){
            System.out.print(iterator.next());
            while(iterator.hasNext()){
                System.out.print(", " + iterator.next());
            }
        }
        System.out.print(")");
    }

}
