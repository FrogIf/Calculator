package sch.frog.test.impl;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import sch.frog.test.ICaseObject;
import sch.frog.test.util.TestUtil;
import sch.frog.calculator.util.IComparator;
import sch.frog.calculator.util.collection.AVLTreeSet;
import sch.frog.calculator.util.collection.ArrayList;
import sch.frog.calculator.util.collection.IList;
import sch.frog.calculator.util.collection.ISet;
import sch.frog.calculator.util.collection.Iterator;
import sch.frog.calculator.util.collection.RBTreeSet;
import sch.frog.test.AbstractTestContent;

/**
 * 红黑树行为测试
 */
public class RBTreeTest extends AbstractTestContent<RBTreeTestCase>{

    @Override
    protected RBTreeTestCase generateCase() {
        RBTreeTestCase rbTreeTestCase = new RBTreeTestCase();
        for(int i = 0; i < 100; i++){
            rbTreeTestCase.add(TestUtil.randomString());
        }
        return rbTreeTestCase;
    }

    @Override
    protected boolean singleStepTest(RBTreeTestCase caseObj) {
        IList<String> list = caseObj.getTestList();
        ISet<String> rbSet = new RBTreeSet<>(COMPARATOR);
        
        ISet<String> avlSet = new AVLTreeSet<>(COMPARATOR);

        Set<String> standardSet = new TreeSet<>(COMPARATOR2);
        
        // 测试add
        Iterator<String> itr = list.iterator();
        while(itr.hasNext()){
            String str = itr.next();
            boolean rbAdd = rbSet.add(str);
            boolean avlAdd = avlSet.add(str);
            boolean ansAdd = standardSet.add(str);

            boolean c = rbAdd == avlAdd && ansAdd == rbAdd;
            if(!c){ return false; }
        }

        // 测试contains
        itr = list.iterator();
        while(itr.hasNext()){
            String str = itr.next();
            if(!avlSet.contains(str) || !rbSet.contains(str)){
                return false;
            }
        }

        // 测试size
        return avlSet.size() == rbSet.size() && avlSet.size() == standardSet.size();
    }

    @Override
    protected RBTreeTestCase parse(String content) {
        String[] arr = content.split(",");
        RBTreeTestCase rbTreeTestCase = new RBTreeTestCase();
        for(int i = 0; i < arr.length; i++){
            rbTreeTestCase.add(arr[i]);
        }
        return rbTreeTestCase;
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


class RBTreeTestCase implements ICaseObject{

    private ArrayList<String> testList = new ArrayList<>();

    public void add(String val){
        testList.add(val);
    }

    public IList<String> getTestList(){
        return this.testList;
    }

    @Override
    public String content() {
        StringBuilder sb = new StringBuilder();
        Iterator<String> itr = testList.iterator();
        boolean isFirst = true;
        while(itr.hasNext()){
            if(isFirst){
                sb.append(',');
                isFirst = false;
            }
            sb.append(itr.next());
        }
        return sb.toString();
    }

}