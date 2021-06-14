package frog.test;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import frog.calculator.util.IComparator;
import frog.calculator.util.collection.AVLTreeSet;
import frog.calculator.util.collection.ArrayList;
import frog.calculator.util.collection.IList;
import frog.calculator.util.collection.ISet;
import frog.calculator.util.collection.Iterator;
import frog.calculator.util.collection.RBTreeSet;
import frog.test.util.DebugUtil;

public class RBTreeTest implements ITest{

    @Override
    public void test() {
        for(int i = 0; i < 1000000; i++){
            if(!repeatCheck()){
                break;
            }
            if(!checkRbTreeContent()){
                break;
            }
        }
    }

    private static boolean checkRbTreeContent(){
        IList<String> list = new ArrayList<>();
        for(int i = 0; i < 100; i++){
            list.add(DebugUtil.randomString());
        }

        return checkContent(list);
    }

    private static boolean checkContent(IList<String> list){
        Set<String> standardSet = new TreeSet<>(COMPARATOR2);
        ISet<String> rbSet = new RBTreeSet<>(COMPARATOR);
        ISet<String> avlSet = new AVLTreeSet<>(COMPARATOR);

        Iterator<String> itr = list.iterator();
        while(itr.hasNext()){
            String str = itr.next();
            standardSet.add(str);
        }

        java.util.Iterator<String> sdItr0 = standardSet.iterator();
        while(sdItr0.hasNext()){
            String str = sdItr0.next();
            rbSet.add(str);
            avlSet.add(str);
        }

        Iterator<String> rbItr = rbSet.iterator();
        while(rbItr.hasNext()){
            String str = rbItr.next();
            if(!standardSet.contains(str)){
                System.out.println("ans not contain : " + str);
                System.out.println("standardSet : [" + setToString(standardSet) + "]");
                return false;
            }
        }

        Iterator<String> avlItr = avlSet.iterator();
        while(avlItr.hasNext()){
            String str = avlItr.next();
            if(!standardSet.contains(str)){
                System.out.println("ans not contain : " + str);
                System.out.println("standardSet : [" + setToString(standardSet) + "]");
                return false;
            }
        }

        java.util.Iterator<String> sdItr = standardSet.iterator();
        while(sdItr.hasNext()){
            String str = sdItr.next();
            if(!rbSet.contains(str)){
                System.out.println("rb not contain : " + str);
                return false;
            }
            if(!avlSet.contains(str)){
                System.out.println("avl not contain : " + str);
                return false;
            }
        }

        if(rbSet.size() != standardSet.size()){
            System.out.println("the size not right. rb : " + rbSet.size() + ", ans : " + standardSet.size());
            return false;
        }

        if(avlSet.size() != standardSet.size()){
            System.out.println("the size not right. avl : " + avlSet.size() + ", ans : " + standardSet.size());
            return false;
        }

        return true;
    }

    /**
     * 测试插入去重
     */
    private static boolean repeatCheck(){
        IList<String> list = new ArrayList<>();
        for(int i = 0; i < 100; i++){
            list.add(DebugUtil.randomString());
        }

        return checkString(list);
    }

    private static boolean checkString(IList<String> list){
        ISet<String> rbSet = new RBTreeSet<>(COMPARATOR);
        
        ISet<String> avlSet = new AVLTreeSet<>(COMPARATOR);

        Set<String> standardSet = new TreeSet<>(COMPARATOR2);
        
        // 重复校验测试
        Iterator<String> itr = list.iterator();
        int i = 0;
        while(itr.hasNext()){
            String str = itr.next();
            boolean rbAdd = rbSet.add(str);
            boolean avlAdd = avlSet.add(str);
            boolean ansAdd = standardSet.add(str);
            if(rbAdd != avlAdd){
                System.out.println("----error----");
                Iterator<String> errItr = list.iterator();
                StringBuilder sb = new StringBuilder();
                while(errItr.hasNext()){
                    sb.append(errItr.next()).append(',');
                }
                System.out.println("origin list : [" + sb.toString() + "]");
                System.out.println("error str : [" + str + "], pos : " + i);
                if(avlAdd == ansAdd){
                    System.out.println("avl is right");
                }else{
                    System.out.println("rb is right");
                }
                return false;
            }
            i++;
        }
        return true;
    }



    private static String setToString(Set<String> set){
        java.util.Iterator<String> itr = set.iterator();
        StringBuilder sb = new StringBuilder();
        while(itr.hasNext()){
            String str = itr.next();
            sb.append(str).append(',');
        }
        return sb.toString();
    }

    private static String isetToString(ISet<String> sets){
        Iterator<String> itr = sets.iterator();
        StringBuilder sb = new StringBuilder();
        while(itr.hasNext()){
            String str = itr.next();
            sb.append(str).append(',');
        }
        return sb.toString();
    }

    private static final IComparator<String> COMPARATOR = new IComparator<String>(){
        @Override
        public int compare(String a, String b) {
            return a.compareTo(b);
        }
    };

    private static final Comparator<String> COMPARATOR2 = new Comparator<String>(){
        @Override
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        } 
    };
    
}
