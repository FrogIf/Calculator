package test;

import frog.calculator.util.collection.Iterator;
import frog.calculator.util.collection.TreeSet;

import java.util.Random;

public class TreeSetTest {

    public static void main(String[] args){
        while(true){
            test();
        }
    }

    private static void test(){
        TreeSet<Integer> treeSet = new TreeSet<>();

        Random r = new Random();

        int count = r.nextInt(100);
        while(treeSet.size() < count){
            treeSet.add(r.nextInt(1000));
        }

        while(!treeSet.isEmpty()){
            Iterator<Integer> iterator = treeSet.iterator();
            while(iterator.hasNext()){
                Integer value = iterator.next();
                int i = r.nextInt(10);
                if(i % 3 == 0){
                    System.out.println("delete : " + value);
                    int oldSize = getSize(treeSet);
                    iterator.remove();
                    int newSize = getSize(treeSet);
                    if(newSize != oldSize - 1){
                        show(treeSet);
                        throw new IllegalStateException("delete failed.");
                    }
                    checkDelFail(treeSet, value);
                }
            }
        }
    }

    private static void show(TreeSet<Integer> treeSet){
        Iterator<Integer> iterator1 = treeSet.iterator();
        while(iterator1.hasNext()){
            System.out.print(iterator1.next() + ",");
        }
        System.out.println("\n");
    }

    private static int getSize(TreeSet<Integer> treeSet){
        Iterator<Integer> iterator = treeSet.iterator();
        int i = 0;
        while (iterator.hasNext()){
            iterator.next();
            i++;
        }
        return i;
    }

    private static void checkDelFail(TreeSet<Integer> treeSet, int delValue){
        Iterator<Integer> iterator1 = treeSet.iterator();
        while(iterator1.hasNext()){
            if(delValue == iterator1.next()){
                show(treeSet);
                throw new IllegalStateException("delete failed.");
            }
        }
        System.out.println("\n");
    }

}
