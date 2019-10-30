package test;

import frog.calculator.util.collection.ISet;
import frog.calculator.util.collection.Iterator;
import frog.calculator.util.collection.RBTreeSet;

public class AVLTreeTest {

    public static void main(String[] args){
        RBTreeSet<Integer> treeSet = new RBTreeSet<>(Integer::compareTo);
//        AVLTreeSet<Integer> treeSet = new AVLTreeSet<>(Integer::compareTo);
//        Tree<Integer> treeSet = new Tree<>(12);
        treeSet.add(1);
        treeSet.add(2);
        treeSet.add(3);
        treeSet.add(4);
        treeSet.add(5);
        treeSet.add(6);
        treeSet.add(7);
        treeSet.add(8);
        treeSet.add(9);
        treeSet.add(10);
        showTree(treeSet);
//        treeSet.remove(190);
//        treeSet.remove(2);
//        treeSet.remove(4);
//        treeSet.remove(6);
        Iterator<Integer> iterator = treeSet.iterator();
        while (iterator.hasNext()){
            Integer next = iterator.next();
            if((next & 1) == 1){
                System.out.println("remove - " + next);
                iterator.remove();
            }
        }
//        treeSet.remove(12);
//        treeSet.remove(14);
//        treeSet.remove(16);
//        treeSet.remove(9);
//        treeSet.remove(8);
//        treeSet.remove(10);
//        treeSet.remove(100);
//        treeSet.remove(2);
//        treeSet.remove(13);
//        treeSet.remove(190);
        showTree(treeSet);
        System.out.println(treeSet.find(12));
        System.out.println(treeSet.find(8));

    }

    public static void showTree(ISet<Integer> set){
        Iterator<Integer> iterator = set.iterator();
        while(iterator.hasNext()){
            System.out.print("," + iterator.next());
        }
        System.out.println();
    }


}
